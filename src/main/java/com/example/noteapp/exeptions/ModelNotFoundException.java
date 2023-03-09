package com.example.noteapp.exeptions;

public class ModelNotFoundException extends RuntimeException{
    public ModelNotFoundException() {
    }

    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
