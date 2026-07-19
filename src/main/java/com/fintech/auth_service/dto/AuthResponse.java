/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.fintech.auth_service.dto;

/**
 *
 * @author Harmony
 */
public record AuthResponse(
        
        String accessToken,
        String tokenType
        //private String refreshToken;
        //private long expiresIn;
        
        ) 
{
    public AuthResponse(String accessToken) {
        this(accessToken, "Bearer");
    }
}
