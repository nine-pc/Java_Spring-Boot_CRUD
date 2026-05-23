package com.gfg.spring_crud.Controller;

import com.gfg.spring_crud.Dto.AuthResponse;
import com.gfg.spring_crud.Dto.LoginRequest;
import com.gfg.spring_crud.Dto.RegisterRequest;
import com.gfg.spring_crud.Entity.User;
import com.gfg.spring_crud.Service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok(
                authService.getUsers()
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                authService.getUserById(id)
        );
    }
}
