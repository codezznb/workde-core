package cn.workde.core.validator;


import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChineseCharValidator implements ConstraintValidator<ChineseChar, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNotEmpty(value)) {
            return value.matches(RegexpConstant.REX_SPECIAL_CHARACTERS);
        }
        return true;
    }

    @Override
    public void initialize(ChineseChar constraintAnnotation) {

    }
}