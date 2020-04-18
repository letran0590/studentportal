package com.example.studentportal.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }
}