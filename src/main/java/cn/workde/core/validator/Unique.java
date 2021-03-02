package cn.workde.core.validator;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhujingang
 * @date 2020/5/27 10:43 AM
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

	String message() default "必须是唯一";

	/**
	 * 字段名默认按照驼峰处理 比如loginName 会被处理成login_name
	 * 当不符合这种规则的时候，可以手动指定字段名
	 * @return
	 */
	String column() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
