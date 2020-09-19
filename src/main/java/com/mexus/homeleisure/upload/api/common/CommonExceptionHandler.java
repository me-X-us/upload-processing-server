package com.mexus.homeleisure.upload.api.common;

import com.mexus.homeleisure.upload.api.common.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

/**
 * 공통적으로 발생하는 Exception Handler
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonExceptionHandler {
    /**
     * 형식에 맞지 않는 BODY요청이 왔을 때
     *
     * @param exception 잘못된 데이터입니다.
     * @return BAD_REQUEST
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleNotYours(HttpMessageNotReadableException exception) {
        String message = Objects.requireNonNull(exception.getRootCause()).getMessage().split("\\(class")[0];
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "2001", message);
    }
}
