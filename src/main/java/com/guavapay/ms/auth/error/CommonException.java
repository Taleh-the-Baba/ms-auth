package com.guavapay.ms.auth.error;

public class CommonException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public CommonException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
