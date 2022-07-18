package com.gilang.demo.security;

import lombok.Data;

@Data
public class ValidateToken {

    private Boolean status;
    private Exception exception;

    public ValidateToken(Boolean status, Exception exception) {
        this.status = status;
        this.exception = exception;
    }
}
