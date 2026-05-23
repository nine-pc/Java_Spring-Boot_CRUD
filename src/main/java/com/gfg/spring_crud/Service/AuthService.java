package com.gfg.spring_crud.Service;

import com.gfg.spring_crud.Entity.User;
import com.gfg.spring_crud.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.gfg.spring_crud.Dto.AuthResponse;
import com.gfg.spring_crud.Dto.LoginRequest;
import com.gfg.spring_crud.Dto.RegisterRequest;
import com.gfg.spring_crud.Security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }
}
