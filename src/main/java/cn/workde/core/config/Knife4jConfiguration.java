package cn.workde.core.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;


@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("项目管理系统接口文档")
                        .description("项目管理系统接口文档")
                        .termsOfServiceUrl("http://www.ydool.com/")
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("1.0")
                .select()
                .apis(withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 所有标识了@Api注解的类都要被扫描到，
     *
     * @return
     */
    public Predicate withClassAnnotation(final Class<? extends Annotation>... annotations) {
        return (input) -> declaringClass((RequestHandler) input).transform(annotationPresent(annotations)).or(false);
    }

    private static Function<Class<?>, Boolean> annotationPresent(final Class<? extends Annotation>... annotations) {
        return input -> hasAnyAnnotation(input, annotations);
    }

    /**
     * 当前类或者其父类、接口是否存在当前注解中的任意一个
     * @param clazz  当前类
     * @param annotations  目标接口
     * @return
     */
    private static boolean hasAnyAnnotation(Class clazz, final Class<? extends Annotation>... annotations) {
        boolean include;
        for (Class<? extends Annotation> annotation : annotations) {
            include = clazz.isAnnotationPresent(annotation);
            if (!include) {
                // 判断实现的接口是否有继承
                Class<?>[] interfaces = clazz.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    include = anInterface.isAnnotationPresent(annotation);
                }
            }
            if (!include) {
                Class superclass = clazz.getSuperclass();
                if(!Object.class.equals(superclass)){
                    return hasAnyAnnotation(superclass,annotations);
                }
            }
            if (include) {
                return true;
            }
        }
        return false;
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}
