package org.example.documentshub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GenericException {
    @ExceptionHandler(UsersNotFoundException.class)
    public ResponseEntity<?> handleException(UsersNotFoundException e){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),e.getMessage(),"users not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),e.getMessage(),"exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),e.getMessage(),"runtime exception");
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<?> handleDocumentsException(DocumentNotFoundException e){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),e.getMessage(),"documents not found");
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
