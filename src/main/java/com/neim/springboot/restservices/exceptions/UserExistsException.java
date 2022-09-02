package com.neim.springboot.restservices.exceptions;

public class UserExistsException extends Exception {

    public UserExistsException(String message) {
        super(message);
    }
}
