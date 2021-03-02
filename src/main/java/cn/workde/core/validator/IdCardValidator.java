package cn.workde.core.validator;

import cn.hutool.core.util.IdcardUtil;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdCardValidator implements ConstraintValidator<IdCard, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNotBlank(value)) {
            return IdcardUtil.isValidCard18(value);
        }
        return true;
    }

    @Override
    public void initialize(IdCard constraintAnnotation) {

    }
}
