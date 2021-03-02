package cn.workde.core.validator;

import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNotBlank(value)) {
            return value.matches(RegexpConstant.REX_MOBILE);
        }
        return true;
    }

    @Override
    public void initialize(Mobile constraintAnnotation) {

    }
}
