package com.bugtracker.controller;

import com.bugtracker.model.User;
import com.bugtracker.security.dto.AuthResponse;
import com.bugtracker.security.dto.LoginRequest;
import com.bugtracker.security.dto.SignupRequest;
import com.bugtracker.security.dto.UserProfileDTO;
import com.bugtracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest request) {
        String token = authService.signup(request.getName(), request.getEmail(), request.getPassword());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return new AuthResponse(token);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserProfileDTO currentUser(Authentication authentication) {
        return authService.getCurrentUserProfile(authentication.getName());
    }

    @GetMapping("/users")
    @PreAuthorize("isAuthenticated()")
    public java.util.List<UserProfileDTO> getAllUsers() {
        return authService.getAllUserProfiles();
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public UserProfileDTO updateProfile(@RequestBody UserProfileDTO profileDTO, Authentication authentication) {
        return authService.updateUserProfile(authentication.getName(), profileDTO);
    }
}
