package com.devEmersonc.microblogging.exception;

public class AccessDeniedException extends org.springframework.security.access.AccessDeniedException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
