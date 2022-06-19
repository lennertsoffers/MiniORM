package com.lennertsoffers.annotation.processor.exception;

public class UnsupportedTypeException extends RuntimeException {
    public UnsupportedTypeException(String message) {
        super(message);
    }
}
