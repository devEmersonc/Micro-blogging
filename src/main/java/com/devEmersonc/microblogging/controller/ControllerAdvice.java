package com.devEmersonc.microblogging.controller;

import com.devEmersonc.microblogging.dto.ErrorMessage;
import com.devEmersonc.microblogging.exception.AccessDeniedException;
import com.devEmersonc.microblogging.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value() + " " + HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.FORBIDDEN.value() + " " + HttpStatus.FORBIDDEN.getReasonPhrase());
        errors.put("error", exception.getMessage());
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("path", request.getRequestURI());

        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }
}
