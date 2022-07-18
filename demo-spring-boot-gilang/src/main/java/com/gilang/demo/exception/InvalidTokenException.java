package com.gilang.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(){
        super("Token invalid!");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
