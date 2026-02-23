package com.elm.expensetracker.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Utility class for Spring Security operations
 * Provides helper methods to access authenticated user information
 */
public class SecurityUtils {

    /**
     * Get the username of the currently authenticated user
     * 
     * How it works:
     * 1. SecurityContextHolder: Thread-local storage that Spring Security uses to store
     *    authentication details for the current request
     * 2. getContext(): Returns the SecurityContext for current thread
     * 3. getAuthentication(): Returns Authentication object containing user details
     * 4. getName(): Returns the username (principal name)
     * 
     * @return username of authenticated user, or null if not authenticated
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        return authentication.getName();
    }

    /**
     * Check if the current user has ADMIN role
     * 
     * Why we need this:
     * - Admins should be able to view/modify ALL expenses (override ownership)
     * - Regular users should only access their own expenses
     * 
     * @return true if current user has ROLE_ADMIN authority
     */
    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            return false;
        }
        
        // Get all authorities (roles) granted to the user
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        // Check if ROLE_ADMIN is present in the authorities
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Check if the current user owns a resource or is an admin
     * 
     * Use case: Before allowing update/delete operations, verify:
     * - The user is the owner of the resource, OR
     * - The user has admin privileges
     * 
     * @param resourceOwnerId The ID of the user who owns the resource
     * @param resourceOwnerUsername The username of the user who owns the resource
     * @return true if current user is owner or admin
     */
    public static boolean isOwnerOrAdmin(Long resourceOwnerId, String resourceOwnerUsername) {
        // Admins can access any resource
        if (isAdmin()) {
            return true;
        }
        
        String currentUsername = getCurrentUsername();
        
        // Check if current user is the owner
        return currentUsername != null && currentUsername.equals(resourceOwnerUsername);
    }
}
