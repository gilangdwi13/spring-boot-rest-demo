package com.gilang.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String message;
    private Object data;
    private Long total_result;
    private Long total_page;

    public Response() {}

    public Response(Object data) {
        this.data = data;
    }

    public Response(String message, Long total_result, Long total_page, Object data) {
        this.message = message;
        this.total_result = total_result;
        this.total_page = total_page;
        this.data = data;
    }

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Response(String message) {
        this.message = message;
    }

}
