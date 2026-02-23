package com.elm.expensetracker.service.impl;

import com.elm.expensetracker.dto.expense.CreateExpenseRequest;
import com.elm.expensetracker.dto.expense.UpdateExpenseRequest;
import com.elm.expensetracker.dto.expense.ExpenseResponse;
import com.elm.expensetracker.exception.UnauthorizedAccessException;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.model.Expense;
import com.elm.expensetracker.model.User;
import com.elm.expensetracker.security.SecurityUtils;
import com.elm.expensetracker.service.interfaces.CategoryService;
import com.elm.expensetracker.service.interfaces.ExpenseService;
import com.elm.expensetracker.service.base.BaseEntityService;
import com.elm.expensetracker.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.elm.expensetracker.repository.ExpenseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl extends BaseEntityService<Expense, ExpenseRepository> implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;
    // NEW: UserService to fetch the authenticated user entity
    private final UserService userService;

    /**
     * Create a new expense
     * 
     * KEY CHANGES:
     * 1. Automatically get the currently authenticated user
     * 2. Assign the expense to that user (ownership)
     * 3. User doesn't need to send userId in request - we get it from JWT token
     */
    @Override
    @Transactional
    public ExpenseResponse createExpense(CreateExpenseRequest request) {
        // Get the category as before
        Category category = categoryService.findById(request.getCategoryId());
        
        // NEW: Get currently authenticated user from SecurityContext
        // SecurityUtils extracts username from JWT token stored in SecurityContext
        String currentUsername = SecurityUtils.getCurrentUsername();
        
        // NEW: Fetch the User entity from database
        User currentUser = userService.findByUsername(currentUsername);

        // Build expense with ALL required fields including owner
        Expense newExpense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .category(category)
                .expenseDate(request.getExpenseDate())
                .user(currentUser)  // NEW: Automatically assign to current user
                .build();

        Expense savedExpense = expenseRepository.save(newExpense);
        return ExpenseResponse.from(savedExpense);
    }

    /**
     * Get a single expense by ID
     * 
     * KEY CHANGES:
     * 1. Check if user has permission to view this expense
     * 2. Only owner or admin can view
     */
    @Override
    public ExpenseResponse getExpense(Long id) {
        Expense expense = findById(id);
        
        // NEW: Verify permission before returning data
        checkOwnershipPermission(expense);
        
        return ExpenseResponse.from(expense);
    }

    /**
     * Update an expense
     * 
     * KEY CHANGES:
     * 1. Verify user has permission before allowing update
     * 2. Only owner or admin can update
     */
    @Override
    @Transactional
    public ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request) {
        Expense expense = findById(id);
        
        // NEW: Check permission before allowing modifications
        checkOwnershipPermission(expense);

        // Apply updates as before
        if (request.getTitle() != null) expense.setTitle(request.getTitle());
        if (request.getDescription() != null) expense.setDescription(request.getDescription());
        if (request.getAmount() != null) expense.setAmount(request.getAmount());
        if (request.getCurrency() != null) expense.setCurrency(request.getCurrency());
        if (request.getCategoryId() != null) expense.setCategory(categoryService.findById(request.getCategoryId()));
        if (request.getExpenseDate() != null) expense.setExpenseDate(request.getExpenseDate());

        return ExpenseResponse.from(expense);
    }

    /**
     * Delete an expense (soft delete)
     * 
     * KEY CHANGES:
     * 1. Verify permission before allowing deletion
     * 2. Only owner or admin can delete
     */
    @Override
    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = findById(id);
        
        // NEW: Check permission before deletion
        checkOwnershipPermission(expense);
        
        if(expense.isDeleted()) return;
        expense.markAsDeleted(true);
    }

    /**
     * Get all expenses
     * 
     * KEY CHANGES:
     * 1. Regular users: Only see THEIR expenses
     * 2. Admins: See ALL expenses (for reporting/management)
     */
    @Override
    public List<ExpenseResponse> getAllExpenses() {
        List<Expense> expenses;
        
        // NEW: Filter expenses based on user role
        if (SecurityUtils.isAdmin()) {
            // Admins see all expenses
            expenses = expenseRepository.findAll();
        } else {
            // Regular users only see their own expenses
            String currentUsername = SecurityUtils.getCurrentUsername();
            User currentUser = userService.findByUsername(currentUsername);
            expenses = expenseRepository.findByUser(currentUser);
        }
        
        return expenses.stream()
                .map(ExpenseResponse::from)
                .toList();
    }

    /**
     * HELPER METHOD: Check if current user has permission to access an expense
     * 
     * Permission granted if:
     * - User is the owner of the expense, OR
     * - User has ADMIN role
     * 
     * @throws UnauthorizedAccessException if user doesn't have permission
     */
    private void checkOwnershipPermission(Expense expense) {
        // Check if current user is owner or admin
        boolean hasPermission = SecurityUtils.isOwnerOrAdmin(
            expense.getUser().getId(),
            expense.getUser().getUsername()
        );
        
        // If no permission, throw 403 Forbidden exception
        if (!hasPermission) {
            throw UnauthorizedAccessException.forExpense(expense.getId());
        }
    }

    @Override
    protected ExpenseRepository getRepository() {
        return expenseRepository;
    }

    @Override
    protected String getEntityName() {
        return "Expense";
    }
}
