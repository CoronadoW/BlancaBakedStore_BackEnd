package com.coronado.blancabakedstore.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> EntityNotFoundExceptionHandler(EntityNotFoundException exep){
        return new ResponseEntity<>(exep.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<String> EntityAlreadyExistsExceptionHandler(EntityAlreadyExistsException exep){
        return new ResponseEntity<>(exep.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> UnauthorizedExceptionHandler(UnauthorizedException exep){
        return new ResponseEntity<>(exep.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
