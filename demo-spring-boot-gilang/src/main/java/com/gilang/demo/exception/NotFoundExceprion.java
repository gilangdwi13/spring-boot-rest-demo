package com.gilang.demo.exception;

public class NotFoundExceprion extends RuntimeException {

    private String objectName;
    private String additionalMessage;

    public NotFoundExceprion (String objectName) {
        super(String.format("%s not found!" , objectName));
    }

    public NotFoundExceprion (String objectName, String additionalMessage) {
        super(String.format("%s not found!, %s" , objectName, additionalMessage));
    }
}
