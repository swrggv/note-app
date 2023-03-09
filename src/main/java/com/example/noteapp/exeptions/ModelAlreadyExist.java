package com.example.noteapp.exeptions;

public class ModelAlreadyExist extends RuntimeException{
    public ModelAlreadyExist(String message) {
        super(message);
    }

    public ModelAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }
}
