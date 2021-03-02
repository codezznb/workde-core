package cn.workde.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 中文
 *
 * @author chenchen
 * @date 2020/06/17
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChineseValidator.class)
public @interface Chinese {

	String message() default "姓名必须是中文的";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
