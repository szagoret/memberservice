package com.memberservice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by szagoret
 */
public class DateFormatException extends RuntimeException {

    private Logger logger = LoggerFactory.getLogger(DateFormatException.class);
    private static String errorTemplate = "Date format: %s" + " is invalid. Please use: %s";

    public DateFormatException(String dateValue, String dateFormat) {
        super(String.format(errorTemplate, dateValue, dateFormat));
        logger.error(String.format(errorTemplate, dateValue, dateFormat));
    }

    public DateFormatException(String message) {
        super(message);
        logger.error(message);
    }
}
