package com.example.noteapp.exeptions;

public class NoRootException extends RuntimeException {
    public NoRootException(String message) {
        super(message);
    }

    public NoRootException(String message, Throwable cause) {
        super(message, cause);
    }
}
