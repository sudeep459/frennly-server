package com.frennly.ds.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> messageExceptionHandler(MessageException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetail> chatExceptionHandler(ChatException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetail> handleNoHandlerFoundException(NoHandlerFoundException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail("Endpoint not found", e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest req) {
        String error = e.getBindingResult().getFieldError().getDefaultMessage();
        ErrorDetail err = new ErrorDetail("Validation Error",error, LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> badCredentialsExceptionHandler(BadCredentialsException e, WebRequest req) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

}
