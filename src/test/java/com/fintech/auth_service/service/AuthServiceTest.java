/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.fintech.auth_service.service;

import com.fintech.auth_service.dto.AuthResponse;
import com.fintech.auth_service.dto.RegisterRequest;
import com.fintech.auth_service.entity.User;
import com.fintech.auth_service.exception.EmailAlreadyExistsException;
import com.fintech.auth_service.repository.UserRepository;
import com.fintech.auth_service.security.jwt.JwtTokenProvider;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Harmony
 */

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;
    
    @Test
    void shouldRegisterUserSuccessfully() {
        RegisterRequest request = new RegisterRequest("test@mail.com", "Test User", "password");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(any())).thenReturn("hashed-password");

        when(jwtTokenProvider.generateToken(any(), any())).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.accessToken());
    }
    
    @Test
    void shouldThrowExceptionIfEmailExists() {
        RegisterRequest request = new RegisterRequest("test@mail.com", "Test User", "password");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(new User()));

        assertThrows(
                EmailAlreadyExistsException.class,
                () -> authService.register(request)
        );
    }

}
