/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package com.fintech.auth_service.service;

import com.fintech.auth_service.dto.AuthResponse;
import com.fintech.auth_service.dto.LoginRequest;
import com.fintech.auth_service.dto.RegisterRequest;
import com.fintech.auth_service.entity.User;
import com.fintech.auth_service.exception.EmailAlreadyExistsException;
import com.fintech.auth_service.exception.InvalidCredentialsException;
import com.fintech.auth_service.repository.UserRepository;
import com.fintech.auth_service.security.jwt.JwtTokenProvider;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Harmony
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    
    
    public AuthResponse register(RegisterRequest request) {
        
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User();
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        List<String> roles = List.of("ROLE_USER");

        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                roles
        );

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        List<String> roles = List.of("ROLE_USER");

        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                roles
        );

        return new AuthResponse(token);
    }
}
