package com.fileapi.demo.services;

import com.fileapi.demo.dtos.AuthResponse;
import com.fileapi.demo.dtos.LoginUserRequest;
import com.fileapi.demo.dtos.RegisterUserRequest;
import com.fileapi.demo.models.User;
import jakarta.security.auth.message.AuthException;

import java.util.Optional;

public interface IUserService {
    User register(RegisterUserRequest request);
    AuthResponse login(LoginUserRequest request) throws AuthException;
    User getCurrentUser();
    Optional<User> getUserByUsername(String username);
}
