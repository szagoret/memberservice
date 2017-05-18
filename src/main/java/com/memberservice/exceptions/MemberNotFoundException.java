package com.memberservice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by szagoret
 */
public class MemberNotFoundException extends RuntimeException {

    private Logger logger = LoggerFactory.getLogger(MemberNotFoundException.class);

    private static String errorTemplate = "Could not find member with ID: %d";

    public MemberNotFoundException(Long memberId) {
        super(String.format(errorTemplate, memberId));
        logger.error(String.format(errorTemplate, memberId));
    }

    public MemberNotFoundException(String message) {
        super(message);
        logger.error(message);
    }
}
