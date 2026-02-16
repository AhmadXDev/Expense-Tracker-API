package com.elm.expensetracker.service;

import com.elm.expensetracker.dto.user.RegisterRequest;
import com.elm.expensetracker.dto.user.UserResponse;
import com.elm.expensetracker.model.User;

public interface UserService {

    public UserResponse registerUser(RegisterRequest request);

    User findById(Long id);

    User findByUsername(String username);
}
