package com.fileapi.demo.services;

import com.fileapi.demo.dtos.AuthResponse;
import com.fileapi.demo.dtos.LoginUserRequest;
import com.fileapi.demo.dtos.RegisterUserRequest;
import com.fileapi.demo.exceptions.InvalidCredentialsException;
import com.fileapi.demo.exceptions.UserAlreadyExistsException;
import com.fileapi.demo.exceptions.UserNotLoggedInException;
import com.fileapi.demo.models.User;
import com.fileapi.demo.repositories.IUserRepository;
import com.fileapi.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;

@Service
@SessionScope  // Användaren per session
@RequiredArgsConstructor
public class DefaultUserService implements IUserService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("User with that username already exists.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(new Date());

        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginUserRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotLoggedInException("Not logged in");
        }

        return (User) authentication.getPrincipal();
    }
}
