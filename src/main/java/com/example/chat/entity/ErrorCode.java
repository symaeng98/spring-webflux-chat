package com.example.chat.entity;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "COMMON-ERR-500", "INTERNAL SERVER ERROR"),
    UNAUTHORIZED_ERROR(401, "COMMON-ERR-401", "UNAUTHORIZED ERROR");

    private final int status;
    private final String errorCode;
    private final String message;

    private ErrorCode(int status, String errorCode, String message){
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
