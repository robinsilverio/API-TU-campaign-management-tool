package com.example.tu_campaign_management_tool_api.exception;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error " + HttpStatus.FORBIDDEN + " " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body("Invalid request body: " + e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("Error " + HttpStatus.BAD_REQUEST + ": " + e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleWritableException(HttpMessageNotWritableException e) {
        return ResponseEntity.internalServerError().body("Error " + HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.internalServerError().body("Error: " + HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body("Error: " + HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleSqlConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.internalServerError().body("Error: " + HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleRequestMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Error " + HttpStatus.METHOD_NOT_ALLOWED + ": " + e.getMessage());
    }
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error 404 NOT_FOUND - Campaign not found.");
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleInvalidDefinitionException(InvalidDefinitionException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
