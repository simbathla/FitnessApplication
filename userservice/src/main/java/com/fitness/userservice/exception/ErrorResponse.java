package com.fitness.userservice.exception;


public class ErrorResponse {
    private String errorCode;
    private String errorMsg;

    public ErrorResponse(String errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
