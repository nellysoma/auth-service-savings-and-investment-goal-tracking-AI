/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package com.fintech.auth_service.service;

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
