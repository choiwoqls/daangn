package com.side.daangn.exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String message){
        super(message);
    }
}
