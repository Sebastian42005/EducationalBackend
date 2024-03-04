package com.example.educationalbackend.exception.exceptions;

public class UserNotLoggedInException extends RuntimeException {

    public UserNotLoggedInException() {
        super("User not logged in");
    }
}
