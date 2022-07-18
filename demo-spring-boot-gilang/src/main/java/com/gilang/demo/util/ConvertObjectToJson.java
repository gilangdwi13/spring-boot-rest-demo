package com.gilang.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ConvertObjectToJson {

    public static final String create(Object object) throws IOException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static final String createWithJackson(Object o) {
        String result = "";
        try {
            result = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}