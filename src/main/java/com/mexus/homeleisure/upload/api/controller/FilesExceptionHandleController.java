package com.mexus.homeleisure.upload.api.controller;

import com.mexus.homeleisure.upload.api.common.response.ErrorResponse;
import com.mexus.homeleisure.upload.api.exception.FileNameException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FilesExceptionHandleController {

    @ExceptionHandler(FileNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCantUploadFile(FileNameException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "303", exception.getMessage());
    }
}
