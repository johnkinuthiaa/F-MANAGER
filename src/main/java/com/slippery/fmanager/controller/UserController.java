package com.slippery.fmanager.controller;

import com.slippery.fmanager.dto.UserDto;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user){
        return ResponseEntity.ok(userService.register(user));
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody User user){
        return ResponseEntity.ok(userService.login(user));
    }
}
