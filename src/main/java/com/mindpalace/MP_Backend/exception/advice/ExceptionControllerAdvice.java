package com.mindpalace.MP_Backend.exception.advice;

import com.mindpalace.MP_Backend.exception.ErrorResponse;
import com.mindpalace.MP_Backend.exception.ErrorResponseForSignup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse authenticExHandle(AuthenticationException e) {
        return new ErrorResponse("UNAUTHORIZED", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse numberExHandle(MethodArgumentTypeMismatchException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponse("BAD", "잘못된 요청입니다");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseForSignup numberExHandle(MethodArgumentNotValidException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResponseForSignup response = new ErrorResponseForSignup("BAD", "잘못된 요청입니다");
        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        System.out.println("response.getValidation() = " + response.getValidation());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", "잘못된 요청입니다");
        return new ErrorResponse("BAD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponse exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponse("EX", "내부 오류");
    }
}
