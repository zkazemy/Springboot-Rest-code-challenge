package com.kazemi.challenge.challenge.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class BusinessException extends RuntimeException {
    private final BusinessExceptionType errorType;
    private String message;
    private HttpStatus status;
    private Map<String, Object> data;

    public BusinessException(BusinessExceptionType errorType, String message, HttpStatus status, Map<String, Object> data) {
        super(message);
        this.status = status;
        this.errorType = errorType;
        this.message = message;
        this.data = data;
    }

    public BusinessException(BusinessExceptionType errorType, String message, HttpStatus status, Map<String, Object> data, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorType = errorType;
        this.message = message;
        this.data = data;
    }

    public BusinessException(BusinessExceptionType errorType, String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.errorType = errorType;
        this.message = message;
    }

    public BusinessException(BusinessExceptionType errorType, String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorType = errorType;
        this.message = message;
    }

    public BusinessException(BusinessExceptionType errorType, HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
        this.errorType = errorType;
    }

    public BusinessException(BusinessExceptionType errorType) {
        this.errorType = errorType;
    }

    public BusinessException(BusinessExceptionType errorType, String message) {
        super(message);
        this.errorType = errorType;
        this.message = message;
    }

    public BusinessException(BusinessExceptionType errorType, Throwable cause) {
        super(cause);
        this.errorType = errorType;
    }

    public BusinessException(BusinessExceptionType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
        this.message = message;
    }

    public BusinessException(BusinessExceptionType errorType, HttpStatus status) {
        this.errorType = errorType;
        this.status = status;
    }

    public BusinessException(BusinessExceptionType errorType, Map<String, Object> data) {
        this.errorType = errorType;
        this.data = data;
    }

    public BusinessException(BusinessExceptionType errorType, String message, Map<String, Object> data) {
        super(message);
        this.errorType = errorType;
        this.data = data;
    }

    public BusinessException(BusinessExceptionType errorType, String message, Throwable cause, Map<String, Object> data) {
        super(message, cause);
        this.errorType = errorType;
        this.data = data;
    }
}
