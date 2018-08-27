package com.miao.exception;

import com.miao.common.ServerResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ServerResponse<String> exceptionHandler(HttpServletRequest request,Exception e) {
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return ServerResponse.createByErrorCodeMessage(ex.getCodeMsg().getCode(),ex.getCodeMsg().getMsg());
        }

        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            String msg = "请求不合法：";
            for(ConstraintViolation<?> s:ex.getConstraintViolations()){
                msg += s.getInvalidValue()+s.getMessage();
            }

            return ServerResponse.createByErrorCodeMessage(3, msg);
        }

        if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();

            return ServerResponse.createByErrorCodeMessage(1, msg);
        }

        return ServerResponse.createByErrorCodeMessage(2, "通用异常");

    }

}
