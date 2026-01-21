package com.fileapi.demo.services;

import com.fileapi.demo.dtos.LoginUserRequest;
import com.fileapi.demo.dtos.RegisterUserRequest;
import com.fileapi.demo.models.User;

public interface IUserService {
    User register(RegisterUserRequest request);
    User login(LoginUserRequest request);
    User getCurrentUser();
}
