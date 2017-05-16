package com.memberservice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by szagoret
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {

	private Logger logger = LoggerFactory.getLogger(MemberNotFoundException.class);

	private static String errorTemplate = "Could not find member with ID: %d";

	public MemberNotFoundException(Long memberId) {
		super(String.format(errorTemplate, memberId));
		logger.error(String.format(errorTemplate, memberId));
	}
}
