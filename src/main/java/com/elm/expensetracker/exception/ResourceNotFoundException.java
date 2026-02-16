package com.elm.expensetracker.exception;

public class ResourceNotFoundException extends RuntimeException {
    public <T> ResourceNotFoundException(String resource, T identifier){
        super(resource + " with " + identifier + " is not found");
    }

}
