package com.memberservice.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.memberservice.exceptions.DateFormatException;
import com.memberservice.exceptions.MemberNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Created by szagoret
 */
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestResponseExceptionHandler.class);

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<?> handleDateFormatException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

//    @ExceptionHandler({HttpMessageNotReadableException.class})
//    public ResponseEntity handleBindingErrors(HttpMessageNotReadableException ex) {
//        // do whatever you want with the exceptions
//        Throwable throwable = ex.getCause();
//        if (throwable instanceof InvalidFormatException) {
//            InvalidFormatException exception = (InvalidFormatException) ex.getCause();
//            if (exception.getTargetType().isAssignableFrom(Date.class)) {
//                throw new DateFormatException(exception.getValue().toString());
//            }
//        }
//        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
//    }
}
