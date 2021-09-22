package com.epam.rd.library.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleDataNotFoundException(DataNotFoundException exception, WebRequest request){
        Map<String,String> response = new HashMap<>();
        response.put("message",exception.getMessage());
        response.put("timestamp",new Date().toString());
        response.put("uri",request.getDescription(false));
        response.put("status-code",HttpStatus.NOT_FOUND.name());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<Map<String,String>> handleDuplicateDataException(DuplicateDataException exception, WebRequest request){
        Map<String,String> response = new HashMap<>();
        response.put("message",exception.getMessage());
        response.put("timestamp",new Date().toString());
        response.put("uri",request.getDescription(false));
        response.put("status-code",HttpStatus.CONFLICT.name());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String,String>> handleFeignException(FeignException exception, WebRequest request){
        Map<String,String> response = new HashMap<>();
        response.put("message",exception.getMessage());
        response.put("timestamp",new Date().toString());
        response.put("uri",request.getDescription(false));
        response.put("status-code",HttpStatus.valueOf(exception.status()).name());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.status()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String,String>> handleConstraintViolation(
            MethodArgumentNotValidException ex,
            WebRequest request)
    {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        Map<String,String> response = new HashMap<>();
        response.put("message",errors.toString());
        response.put("timestamp",new Date().toString());
        response.put("uri",request.getDescription(false));
        response.put("status-code",HttpStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGlobalException(Exception exception, WebRequest request){
        Map<String,String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        response.put("timestamp",new Date().toString());
        response.put("uri",request.getDescription(false));
        response.put("status-code",HttpStatus.INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
