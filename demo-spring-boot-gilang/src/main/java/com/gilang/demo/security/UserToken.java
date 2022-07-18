package com.gilang.demo.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserToken {

    @NotBlank
    private String authentication;

    @NotBlank
    private String refresh;

    private Date expiredRefreshToken;

    public UserToken(){}

    public UserToken(String authentication, String refresh, Date expiredRefreshToken) {
        this.authentication = authentication;
        this.refresh = refresh;
        this.expiredRefreshToken = expiredRefreshToken;
    }
}
