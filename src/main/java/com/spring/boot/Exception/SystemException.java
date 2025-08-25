package com.spring.boot.Exception;
import org.springframework.http.HttpStatus;

public class SystemException extends RuntimeException {
    private final HttpStatus status;
    public SystemException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;    }
}
