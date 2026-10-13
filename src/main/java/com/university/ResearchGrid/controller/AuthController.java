package com.university.ResearchGrid.controller;

import com.university.ResearchGrid.model.Role;
import com.university.ResearchGrid.model.User;
import com.university.ResearchGrid.repository.UserRepository;
import com.university.ResearchGrid.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> payload) {
        User user = new User();
        user.setEmail(payload.get("email"));
        user.setPassword(passwordEncoder.encode(payload.get("password"))); // Never store plain text
        user.setRole(Role.valueOf(payload.get("role").toUpperCase()));

        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> payload) {
        User user = userRepository.findByEmail(payload.get("email"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(payload.get("password"), user.getPassword())) {
            String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}