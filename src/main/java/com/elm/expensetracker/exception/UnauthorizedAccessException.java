package com.elm.expensetracker.exception;

/**
 * Custom exception for unauthorized access attempts
 * Thrown when a user tries to access/modify a resource they don't own
 * (unless they are an admin)
 */
public class UnauthorizedAccessException extends RuntimeException {
    
    public UnauthorizedAccessException(String message) {
        super(message);
    }
    
    /**
     * Factory method for expense access denial
     */
    public static UnauthorizedAccessException forExpense(Long expenseId) {
        return new UnauthorizedAccessException(
            "You do not have permission to access expense with ID: " + expenseId
        );
    }
}
