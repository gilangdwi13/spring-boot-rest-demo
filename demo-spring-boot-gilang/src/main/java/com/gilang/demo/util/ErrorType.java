package com.gilang.demo.util;

public class ErrorType {
    public static final String
            VALID = "VALID",

    INTERNAL_SERVER_ERROR = "Internal server error",
            WORDING_INTERNAL_SERVER_ERROR = "Oops! something went wrong. Please contact your system administrator",

    BAD_CREDENTIALS = "Bad credentials",
            WORDING_INVALID_USERNAME_PASSWORD = "Invalid username & password",
            WORDING_INVALID_USERNAME_PASSWORD_COUNT = "Invalid username & password. You have %s attempt(s) before your account is suspended",
            WORDING_INVALID_USERNAME_PASSWORD_BLOCKED = "Invalid username & password. Your account has been suspended. Please check your e-mail to recover your account",
            WORDING_INVALID_USERNAME_PASSWORD_EXPIRED = "Your account has been suspended. Please contact your system administrator to recover your account",

    INVALID_ACCESS_TOKEN = "Invalid access token",
            WORDING_INVALID_ACCESS_TOKEN = "Invalid token",

    INVALID_USER_SESSION_TOKEN = "Invalid user session token",
            WORDING_INVALID_USER_SESSION_TOKEN = "Your session token are invalid. You have logged in to another device.",

    INVALID_REFRESH_TOKEN = "Invalid refresh token",
            WORDING_INVALID_REFRESH_TOKEN = "Your refresh token are invalid",

    ACCESS_TOKEN_EXPIRED = "Access token expired",
            WORDING_ACCESS_TOKEN_EXPIRED = "Your token has been expired",

    REFRESH_TOKEN_EXPIRED = "Refresh token expired",
            WORDING_REFRESH_TOKEN_EXPIRED = "Your refresh token has been expired",

    USER_SESSION_TOKEN_EXPIRED = "User session token expired",
            WORDING_USER_SESSION_TOKEN_EXPIRED = "Your user session token has been expired",

    FAILED_REALLOCATE_LEAD_RM = "Lead failed to reallocate",

    CALLREPORT_CANNOT_CREATE_PDF = "Something went wrong, cannot create PDF document",
            CALLREPORT_HTML_CONTENT_INVALID = " content are invalid, please check again and ensure there're no unexpected character in the content.",

    FAILED_DELETE_ACTIVITY ="Activity failed to delete",

    BAD_REQUEST = "Bad Request",
            INVALID_REQUEST_PARAM = "Invalid request params.",
            FAILED_SAVED_DOCUMENTS ="Documents failed to save",
            TIME_OUT_READ_DATA ="Timeout read data",
            ONLY_DIR_SPV_TH_CARETAKER = "Only direct supervisor to TH can be set as caretaker!"
                    ;


}
