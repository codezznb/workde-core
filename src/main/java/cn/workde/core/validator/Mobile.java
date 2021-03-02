package cn.workde.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 电话号码 -> XXX-XXXXXXX"、"XXXX-XXXXXXXX"
 *
 * @author chenchen
 * @date 2020/06/17
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {

	String message() default "电话号码不正确";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}