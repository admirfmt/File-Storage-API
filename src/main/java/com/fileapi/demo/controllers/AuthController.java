package com.fileapi.demo.controllers;

import com.fileapi.demo.dtos.ErrorResponse;
import com.fileapi.demo.dtos.LoginUserRequest;
import com.fileapi.demo.dtos.RegisterUserRequest;
import com.fileapi.demo.exceptions.UserAlreadyExistsException;
import com.fileapi.demo.exceptions.UserLoginFailedException;
import com.fileapi.demo.exceptions.UserNotLoggedInException;
import com.fileapi.demo.models.User;
import com.fileapi.demo.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        try {
            User user = userService.register(request);
            return ResponseEntity.ok(user);
        } catch (UserAlreadyExistsException ignored) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("User with " + request.getUsername() + " already exists"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request) {
        try {
            User user = userService.login(request);
            return ResponseEntity.ok(user);
        } catch (UserNotLoggedInException ignored) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("You need to register first."));
        }
    }
}
