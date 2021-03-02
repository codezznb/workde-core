package cn.workde.core.validator;

import cn.hutool.core.util.ReUtil;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChineseValidator implements ConstraintValidator<Chinese, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isNotEmpty(value)) {
            return ReUtil.isMatch("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*",value);
        }
        return true;
    }

    @Override
    public void initialize(Chinese constraintAnnotation) {

    }
}
