package cn.workde.core.validator;

import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberLetterValidator implements ConstraintValidator<NumberLetter, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNotBlank(value)) {
            return value.matches(RegexpConstant.REX_NUMBER_LETTER);
        }
        return true;
    }

    @Override
    public void initialize(NumberLetter constraintAnnotation) {

    }
}
