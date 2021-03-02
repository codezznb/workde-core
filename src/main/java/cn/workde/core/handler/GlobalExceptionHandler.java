package cn.workde.core.handler;

import cn.workde.core.common.Ret;
import cn.workde.core.exception.ResultException;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Ret globalException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        Ret ret = Ret.fail().msg("服务器错误，请联系管理员");
        return ret;
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Ret handlerConstraintViolationException(ConstraintViolationException e) {
        return Ret.fail().msg(e.getConstraintViolations().iterator().next().getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Ret handlerBindExceptionn(BindException e) {
        for (Field field : e.getTarget().getClass().getDeclaredFields()) {
            FieldError fieldError = e.getBindingResult().getFieldError(field.getName());
            if (fieldError != null) {
                if(fieldError.getDefaultMessage().contains("NumberFormatException")){
                    ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                    return Ret.fail().msg(annotation!=null?annotation.value()+"只能是数字类型！":fieldError.getDefaultMessage());
                }
                return Ret.fail().msg(fieldError.getDefaultMessage());
            }
        }
        return Ret.fail().msg(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Ret handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, Object> maps = null;
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            maps = new HashMap<>(fieldErrors.size());
            Map<String, Object> finalMaps = maps;
            fieldErrors.forEach(error -> finalMaps.put(error.getField(), error.getDefaultMessage()));
        }
        return Ret.fail().msg("参数校验失败").data(maps);
    }

    @ResponseBody
    @ExceptionHandler(ResultException.class)
    public Ret handlerResultException(ResultException e) {
        return e.getResult();
    }

}
