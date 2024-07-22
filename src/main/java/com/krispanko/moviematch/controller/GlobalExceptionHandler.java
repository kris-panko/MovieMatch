package com.krispanko.moviematch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Add constants here
    public static final String GENERAL_ERROR_MESSAGE = "An error occurred: ";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error(GENERAL_ERROR_MESSAGE, e);
        return new ResponseEntity<>(GENERAL_ERROR_MESSAGE + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
