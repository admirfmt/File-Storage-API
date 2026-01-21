package com.fileapi.demo.controllers;

import com.fileapi.demo.dtos.LoginUserRequest;
import com.fileapi.demo.dtos.RegisterUserRequest;
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
        User user = userService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request) {
        User user = userService.login(request);
        return ResponseEntity.ok(user);
    }
}
