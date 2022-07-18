package com.gilang.demo.exception;

import com.gilang.demo.util.ConvertObjectToJson;
import com.gilang.demo.util.ErrorType;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ThrowCustomAuthenticationError {

    public static final void expiredAccessToken (HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(ConvertObjectToJson.create(new BaseException(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNAUTHORIZED.value(),
                ErrorType.ACCESS_TOKEN_EXPIRED,
                ErrorType.WORDING_ACCESS_TOKEN_EXPIRED,
                request.getRequestURI()
        )));
    }

    public static final void invalidAccessToken (HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(ConvertObjectToJson.create(new BaseException(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNAUTHORIZED.value(),
                ErrorType.INVALID_ACCESS_TOKEN,
                ErrorType.WORDING_INVALID_ACCESS_TOKEN,
                request.getRequestURI()
        )));
    }

    public static final void invalidUserSessionToken (HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(ConvertObjectToJson.create(new BaseException(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNAUTHORIZED.value(),
                ErrorType.INVALID_USER_SESSION_TOKEN,
                ErrorType.WORDING_INVALID_USER_SESSION_TOKEN,
                request.getRequestURI()
        )));
    }

    public static final void expiredUserSessionToken (HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(ConvertObjectToJson.create(new BaseException(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
                HttpStatus.UNAUTHORIZED.value(),
                ErrorType.USER_SESSION_TOKEN_EXPIRED,
                ErrorType.WORDING_USER_SESSION_TOKEN_EXPIRED,
                request.getRequestURI()
        )));
    }

}
