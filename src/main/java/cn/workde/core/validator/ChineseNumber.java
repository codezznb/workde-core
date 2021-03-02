package cn.workde.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 中文、数字
 *
 * @author chenchen
 * @date 2020/06/17
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChineseNumberValidator.class)
public @interface ChineseNumber {

	String message() default "只能包含中文和数字";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}