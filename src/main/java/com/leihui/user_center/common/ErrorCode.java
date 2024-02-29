package com.leihui.user_center.common;

/**
 * Error Code
 */
public enum ErrorCode {
    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"Request Params error",""),
    NOT_FOUND(40001,"Not found",""),
    NO_AUTH(40100,"No access",""),
    NOT_LOGIN(40101,"Not Login",""),
    SYSTEM_ERROR(50000, "Internal System Error", "");
    private final int code;

    /**
     * status code message
     */
    private final String message;
    /**
     * status code description
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
