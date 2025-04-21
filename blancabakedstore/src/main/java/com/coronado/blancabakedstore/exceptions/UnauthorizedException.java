package com.coronado.blancabakedstore.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message){
        super(message);
    }
}
