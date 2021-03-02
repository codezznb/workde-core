package cn.workde.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 数字
 *
 * @author chenchen
 * @date 2020/08/11
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberValidator.class)
public @interface Number {

	String message() default "只能包含数字";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}
