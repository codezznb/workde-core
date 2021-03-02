package cn.workde.core.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 身份证号码
 *
 * @author chenchen
 * @date 2020/06/17
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCardValidator.class)
public @interface IdCard {

	String message() default "身份证号码不正确";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
