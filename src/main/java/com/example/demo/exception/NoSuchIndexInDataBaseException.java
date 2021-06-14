package com.example.demo.exception;

public class NoSuchIndexInDataBaseException extends RuntimeException {
    public NoSuchIndexInDataBaseException(String message) {
        super(message);
    }
}