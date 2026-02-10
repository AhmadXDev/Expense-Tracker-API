package com.elm.expensetracker.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){ 
        super(message); 
    }

}
