package com.fileapi.demo.services;

import com.fileapi.demo.dtos.LoginUserRequest;
import com.fileapi.demo.dtos.RegisterUserRequest;
import com.fileapi.demo.models.User;
import com.fileapi.demo.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;

@Service
@SessionScope  // Användaren per session
@RequiredArgsConstructor
public class DefaultUserService implements IUserService {

    private final IUserRepository userRepository;
    private User currentUser;

    @Override
    public User register(RegisterUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());  // TODO: hasha senare
        user.setCreatedAt(new Date());

        return userRepository.save(user);
    }

    @Override
    public User login(LoginUserRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        currentUser = user;
        return user;
    }

    @Override
    public User getCurrentUser() {
        if (currentUser == null) {
            throw new RuntimeException("Not logged in");
        }
        return currentUser;
    }
}
