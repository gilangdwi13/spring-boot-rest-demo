package com.gilang.demo.exception;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BaseException {
    @NonNull
    private String timestamp;
    @NonNull private Integer status;
    @NonNull private String error;
    @NonNull private String message;
    @NonNull private String path;
}
