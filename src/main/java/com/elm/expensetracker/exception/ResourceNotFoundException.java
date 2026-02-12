package com.elm.expensetracker.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id){
        super(resource + " with id " + id + " is not found");
    }

}
