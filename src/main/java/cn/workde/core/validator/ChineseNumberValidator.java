package cn.workde.core.validator;

import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChineseNumberValidator implements ConstraintValidator<ChineseNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNotBlank(value)) {
            return value.matches(RegexpConstant.REX_CHINESE_NUMBER);
        }
        return true;
    }

    @Override
    public void initialize(ChineseNumber constraintAnnotation) {

    }
}
