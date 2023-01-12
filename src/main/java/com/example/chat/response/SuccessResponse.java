package com.example.chat.response;

public class SuccessResponse {
    private boolean status;
    private String message;
    private Object data;

    public SuccessResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
