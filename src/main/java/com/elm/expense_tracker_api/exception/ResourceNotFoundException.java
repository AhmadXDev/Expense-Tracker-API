package com.elm.expense_tracker_api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){ 
        super(message); 
    }

}
