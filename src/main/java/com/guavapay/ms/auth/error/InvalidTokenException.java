package com.guavapay.ms.auth.error;

public class InvalidTokenException extends CommonException {

    public InvalidTokenException() {
        super("Invalid token");
    }
}
