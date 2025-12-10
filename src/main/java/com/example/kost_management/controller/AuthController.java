package com.example.kost_management.controller;

import com.example.kost_management.model.User;
import com.example.kost_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findByEmailAndPassword(
            loginRequest.getEmail(), 
            loginRequest.getPassword()
        );

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).body("Email atau Password Salah!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        // 1. Cek Email Kembar
        Optional<User> existingUser = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(newUser.getEmail()))
                .findFirst();

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email sudah terdaftar!");
        }

        // 2. Cek Role (Jika kosong, default ke USER)
        if (newUser.getRole() == null || newUser.getRole().isEmpty()) {
            newUser.setRole("USER");
        }
        
        // Simpan
        userRepository.save(newUser);
        return ResponseEntity.ok("Registrasi Berhasil!");
    }
}