package cn.workde.core.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 中文，英文，数字，标点符号
 *
 * @author chenchen
 * @date 2020/06/17
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChineseCharValidator.class)
public @interface ChineseChar {

	String message() default "不能包含特殊字符";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
