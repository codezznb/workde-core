package cn.workde.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author liuhaoze
 * @date 2020/8/17 9:44
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberLetterValidator.class)
public @interface NumberLetter {

	String message() default "只能包含数字和字母";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
