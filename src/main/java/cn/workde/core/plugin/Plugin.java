package cn.workde.core.plugin;

import cn.workde.WorkdeApplication;
import cn.workde.core.exception.WorkdeException;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

public abstract class Plugin {

    private final String name = getClass().getPackage().getName();

    @Getter
    @Setter
    private String dbName = WorkdeApplication.DATA_SOURCE_DEFAULT;

    /**
     * name
     *
     * @return use package name
     */
    public String name() {
        return name;
    }

    @Deprecated
    public EbeanServer ebeanServer() {
        if (hasEntity()) {
            return Ebean.getServer(dbName);
        } else {
            throw new WorkdeException(String.format("插件: %s 没有实体对象, 不支持 ebeanServer 方法 ", name));
        }
    }

    /**
     * need db migration or not
     *
     * @return default true
     */
    public boolean hasEntity() {
        return true;
    }

    /**
     * before grape application run
     */
    public void inTheBeginning() {
    }

    /**
     * after database migration
     */
    public void afterDataBaseMigration() {
    }

    /**
     * after spring loaded
     *
     * @param context spring application context
     */
    public void afterStarted(ApplicationContext context) { }

}
