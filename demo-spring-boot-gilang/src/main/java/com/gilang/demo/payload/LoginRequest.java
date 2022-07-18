package com.gilang.demo.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
