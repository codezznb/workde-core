package cn.workde.core.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 手机号码 -> 11位
 *
 * @author chenchen
 * @date 2020/06/17
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

	String message() default "手机号码格式不正确";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}