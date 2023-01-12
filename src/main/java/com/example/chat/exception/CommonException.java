package com.example.chat.exception;

import com.example.chat.entity.ErrorCode;

public class CommonException extends RuntimeException{
    private ErrorCode errorCode;

    public CommonException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
