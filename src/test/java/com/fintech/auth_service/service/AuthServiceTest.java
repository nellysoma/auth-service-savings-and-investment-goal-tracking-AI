/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.fintech.auth_service.service;

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
        assertEquals("jwt-token", response.getAccessToken());
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
