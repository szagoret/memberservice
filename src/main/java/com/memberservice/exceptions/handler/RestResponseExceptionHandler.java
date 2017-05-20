package com.memberservice.exceptions.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.memberservice.exceptions.DateFormatException;
import com.memberservice.exceptions.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Created by szagoret
 */
@RestControllerAdvice
public class RestResponseExceptionHandler {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<?> handleDateFormatException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity handleBindingErrors(HttpMessageNotReadableException ex) {
        String errorMessage = ex.getMessage();
        Throwable throwable = ex.getCause();
        if (throwable instanceof InvalidFormatException) {
            InvalidFormatException exception = (InvalidFormatException) ex.getCause();
            if (exception.getTargetType().isAssignableFrom(Date.class)) {
                errorMessage = new DateFormatException(exception.getValue().toString(), dateFormat).getMessage();
            }
        }
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
