package cn.workde;

import cn.workde.core.db.CurrentUser;
import cn.workde.core.db.encypt.BasicEncyptKeyManager;
import cn.workde.core.db.id.UuidGenerator;
import cn.workde.core.exception.WorkdeException;
import cn.workde.core.plugin.Plugin;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.typesafe.config.*;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
@EnableAutoConfiguration
@SpringBootApplication
public class WorkdeApplication {

    private static final Config CONFIG = ConfigFactory.load();
    private static final Properties PROPERTIES = loadProperties();

    public static final ImmutableMap<String, Plugin> PLUGINS = loadPlugins();

    public static final String DATA_SOURCE_DEFAULT = "default";
    private static final String ENABLE_PLUGIN_ALL = "all";

    private static ApplicationContext appContext;

    static {
        loadDataSources();
    }

    public static void main(String[] args) {

        PLUGINS.values().forEach(Plugin::inTheBeginning);

        initSpring();

        log.info("\r\n*******************************************\r\n"
                + "#######		STARTUP SUCCESS\r\n" //
                + "#######		http://localhost:" + getConfig("server.port") + "\r\n"
                + "*******************************************\r\n");

        PLUGINS.values().forEach(plg -> plg.afterStarted(appContext));
    }

    private static void initSpring() {
        log.info("开始初始化 Spring");

        SpringApplication app = new SpringApplication(WorkdeApplication.class);

        app.setBannerMode(Banner.Mode.OFF);
        app.setSources(PLUGINS.keySet());

        app.setDefaultProperties(PROPERTIES);
        appContext = app.run();

        log.info("初始 Spring 结束");
    }

    private static Properties loadProperties() {
        Properties p = new Properties();
        try (InputStream is = WorkdeApplication.class.getResourceAsStream("/application.properties")) {
            p.load(is);
            return p;
        } catch (IOException e) {
            throw new WorkdeException("加载 application.properties 错误，请确认是否存在", e);
        }
    }

    private static String getConfig(@NonNull String name) {
        String value = PROPERTIES.getProperty(name);
        if (Strings.isNullOrEmpty(value)) {
            String msg = String.format("%s 必须存在 application.properties 配置", name);
            log.error("\r\n********************************************************************\r\n"
                    + "#######		配置不存在  \r\n"
                    + "#######		" + msg + "\r\n"
                    + "********************************************************************\r\n");
            throw new WorkdeException(msg);
        }
        return value;
    }

    public static String getConfig(@NonNull String name, String defaultValue) {
        String value = PROPERTIES.getProperty(name);
        if (Strings.isNullOrEmpty(value)) return defaultValue;
        return value;
    }

    @NonNull
    private static ImmutableMap<String, Plugin> loadPlugins() {
        log.info("开始加载插件...");
        Map<String, Plugin> map = Maps.newHashMap();

        ServiceLoader<Plugin> plugins = ServiceLoader.load(Plugin.class);

        ConfigObject dsMap = CONFIG.getConfig("datasource").root();

        for (Plugin plugin : plugins) {
            String name = plugin.name();
            log.info("初始化插件 [{}]" , name);

            if (map.containsKey(name)) {
                String msg = String.format("重复的插件: %s, 插件名必须唯一", name);
                log.error(msg);
                throw new WorkdeException(msg);
            }
            ConfigValue cv = dsMap.get(name);
            if(cv != null && ConfigValueType.STRING.equals(cv.valueType())) {
                plugin.setDbName(String.valueOf(cv.unwrapped()));
                log.info("给插件 [{}] 设置 datasource [{}]", name, cv.unwrapped());
            }
            map.put(name, plugin);
        }

        String enablePlugins = getConfig("plugins.enable");
        if (Strings.isNullOrEmpty(enablePlugins) || ENABLE_PLUGIN_ALL.equalsIgnoreCase(enablePlugins)) {
            log.info("当前所有插件可用\n");
            return ImmutableMap.copyOf(map);
        }else {
            HashSet<String> configSet = Sets.newHashSet(enablePlugins.split(","));

            Sets.SetView<String> enablePluginSet = Sets.intersection(configSet, map.keySet());
            if (!enablePluginSet.isEmpty()) {
                log.info("当前可用插件: [{}]", String.join(", ", enablePluginSet));
            } else {
                log.warn("没有可用的插件");
            }

            Map<String, Plugin> enableMap = map.entrySet().stream().filter(e -> enablePluginSet.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return ImmutableMap.copyOf(enableMap);
        }
    }

    @Deprecated
    private static void loadDataSources() {
        Map<String, ServerConfig> serverMap = loadDataSourcesConfig();
        Set<String> hasEntityPlugins = PLUGINS.values().stream().filter(Plugin::hasEntity).map(Plugin::name).collect(Collectors.toSet());
        log.info("需要设置数据源的插件有: [{}]", String.join(", ", hasEntityPlugins));
        hasEntityPlugins.stream().map(name-> {
            getPlugin(name).setDbName(serverMap.containsKey(name) ? name : DATA_SOURCE_DEFAULT);
            ServerConfig sc = serverMap.get(getPlugin(name).getDbName());
            if (sc != null) {
                sc.addPackage(String.format("%s.entity", name));
            } else {
                throw new WorkdeException(String.format("插件 %s 的数据源没有配置，默认数据源也不存在", name));
            }
            return sc;
        }).distinct().forEach(sc -> {

            log.info("数据源 [{}] 支持包名: [{}].", sc.getName(), String.join(", ", sc.getPackages()));

            EbeanServer es = EbeanServerFactory.create(sc);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> es.shutdown(true, true)));
        });
    }

    @NonNull
    @Deprecated
    private static Map<String, ServerConfig> loadDataSourcesConfig() {
        ConfigObject dsMap = CONFIG.getConfig("datasource").root();

        Map<String, ServerConfig> serverMap = Maps.newHashMap();
        for (Map.Entry<String, ConfigValue> entry : dsMap.entrySet()) {
            String k = entry.getKey();
            ConfigValue v = entry.getValue();

            if (v.valueType() == ConfigValueType.OBJECT) {
                serverMap.put(k, serverConfig(k));
            }
        }

        log.info("数据链接初始化完毕: [{}]", String.join(", ", serverMap.keySet()));
        return ImmutableMap.copyOf(serverMap);
    }

    @Deprecated
    private static ServerConfig serverConfig(String name) {

        ServerConfig sc = new ServerConfig();
        sc.setName(name);

        if (DATA_SOURCE_DEFAULT.equalsIgnoreCase(name)) {
            sc.setDefaultServer(true);
        }
        sc.add(new UuidGenerator());
        sc.setEncryptKeyManager(new BasicEncyptKeyManager());
        sc.setCurrentUserProvider(new CurrentUser());
        sc.loadFromProperties(WorkdeApplication.PROPERTIES);
        sc.setRegister(true);
        sc.setLazyLoadBatchSize(100);

        return sc;
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    public static Plugin getPlugin(@NonNull String pluginName) {
        Preconditions.checkArgument(PLUGINS.containsKey(pluginName), String.format("插件 [%s] 不存在.", pluginName));
        return PLUGINS.get(pluginName);
    }

    @NonNull
    public static <T> T getSpringBean(Class<T> tClass) {
        return appContext.getBean(tClass);
    }

}
