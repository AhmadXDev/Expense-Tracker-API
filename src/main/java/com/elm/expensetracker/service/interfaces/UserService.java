package com.elm.expensetracker.service.interfaces;

import com.elm.expensetracker.dto.user.RegisterRequest;
import com.elm.expensetracker.dto.user.UserResponse;
import com.elm.expensetracker.model.User;

import java.util.List;

public interface UserService {

    public UserResponse registerUser(RegisterRequest request);

    public User findById(Long id);

    public User findByUsername(String username);

    public List<UserResponse> getAllUsers();



}
