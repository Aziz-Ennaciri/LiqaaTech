package com.LiqaaTech.Exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(){}
    public NotFoundException(String message){
        super(message);
    }
}
