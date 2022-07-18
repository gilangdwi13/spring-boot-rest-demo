package com.gilang.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class BaseResponse<T> {

    private T data;

    public BaseResponse () {}

    public BaseResponse (T data) {
        this.data = data;
    }

}
