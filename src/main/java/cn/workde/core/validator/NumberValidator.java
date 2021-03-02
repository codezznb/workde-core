package cn.workde.core.validator;


import cn.hutool.core.lang.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NumberValidator implements ConstraintValidator<Number, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return Validator.isNumber(value.toString());
        }
        return true;
    }

    @Override
    public void initialize(Number constraintAnnotation) {

    }
}