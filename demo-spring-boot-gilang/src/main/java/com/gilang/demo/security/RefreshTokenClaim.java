package com.gilang.demo.security;

import lombok.Data;

@Data
public class RefreshTokenClaim {

    public static final String SUBJECT_REFRESH_TOKEN = "REFRESH_TOKEN";

    private String user;

    public RefreshTokenClaim() {}

    public RefreshTokenClaim(String user) {
        this.user = user;
    }
}
