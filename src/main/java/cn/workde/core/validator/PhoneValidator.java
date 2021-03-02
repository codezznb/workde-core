package cn.workde.core.validator;


import cn.hutool.core.lang.Validator;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNotBlank(value)) {
            return Validator.isMobile(value);
        }
        return true;
    }

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

}
