package com.fitness.userservice.exception;


import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataException.class, CannotCreateTransactionException.class})
    public ResponseEntity<ErrorResponse> handleDatabaseErrors(Exception exception){
        System.err.println("Database error occured: " + exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponse(
                        "Database Unavailable",
                        "The user service is currently unable to connect to the database. Please try again later."
                ));
    }
}
