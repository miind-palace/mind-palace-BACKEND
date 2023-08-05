package com.mindpalace.MP_Backend.exception.advice;


import com.mindpalace.MP_Backend.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    //400 Bad Request
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResult numberExHandle(MethodArgumentTypeMismatchException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("001 (Bad Request)", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("001 (Bad Request)", e.getMessage());
    }

    //401 Unauthorized 나중에 JWT 인증 사용하면 추가 예정 io.jsonwebtoken.ExpiredJwtException
    //403 Forbidden JWT 인증 사용하면 추가 예정 io.jsonwebtoken.IncorrectClaimException


    //404 Not Found
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResult noHandlerExHandle(NoHandlerFoundException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("002 (Not Found)", e.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("004 (Internal sever error)", "내부 오류");
    }

}
