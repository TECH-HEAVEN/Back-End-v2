package com.icebear2n2.techheaven.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomControllerAdvice.class);

    @ExceptionHandler(TechHeavenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler(TechHeavenException e) {
        LOGGER.info("ERROR OCCURS {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorCode() + " : " + e.getMessage());
    }
}
