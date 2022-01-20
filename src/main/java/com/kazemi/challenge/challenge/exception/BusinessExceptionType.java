package com.kazemi.challenge.challenge.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessExceptionType {

    GENERAL(1000, HttpStatus.INTERNAL_SERVER_ERROR, "com.kazemi.challenge.error.general"),
    INVALID_DATA(1001, HttpStatus.BAD_REQUEST, "com.kazemi.challenge.error.bad-data");


    private final int code;
    private final HttpStatus httpStatus;
    private final String messageKey;

    BusinessExceptionType(int code, HttpStatus httpStatus, String messageKey) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;
    }
}
