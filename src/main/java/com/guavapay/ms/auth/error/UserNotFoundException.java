package com.guavapay.ms.auth.error;

public class UserNotFoundException extends CommonException {

    public UserNotFoundException() {
        super("User not found");
    }
}
