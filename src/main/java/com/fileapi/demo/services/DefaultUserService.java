package com.fileapi.demo.services;

import com.fileapi.demo.dtos.AuthResponse;
import com.fileapi.demo.dtos.LoginUserRequest;
import com.fileapi.demo.dtos.RegisterUserRequest;
import com.fileapi.demo.exceptions.UserAlreadyExistsException;
import com.fileapi.demo.exceptions.UserNotLoggedInException;
import com.fileapi.demo.models.User;
import com.fileapi.demo.repositories.IUserRepository;
import com.fileapi.demo.security.JwtService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;
import java.util.Optional;

@Service
@SessionScope  // Användaren per session
@RequiredArgsConstructor
public class DefaultUserService implements IUserService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     * Hashes password before saving.
     *
     * @param request RegisterRequest contains username and password
     * @return A user which has been made
     * @throws UserAlreadyExistsException if user already exists
     */
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

    /**
     * Logins a new user and generates a JWT-token.
     * Verifies password with hashing password in database.
     *
     * @param request LoginRequest contains username and password
     * @return AuthResponse with JWT-token and username
     * @throws AuthException if username or password are wrong
     */
    @Override
    public AuthResponse login(LoginUserRequest request) throws AuthException {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(AuthException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException();
        }

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token, user.getUsername());
    }

    /**
     * Gets a user which has been logged in from Spring Security context.
     *
     * @return Logged in user
     * @throws UserNotLoggedInException if no user is logged in.
     */
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotLoggedInException("Not logged in");
        }

        return (User) authentication.getPrincipal();
    }

    /**
     * Gets a user based on the username.
     * Used from JWT-filter for authentication.
     *
     * @param username Username to search for
     * @return Optional contains user if it exists
     */
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
