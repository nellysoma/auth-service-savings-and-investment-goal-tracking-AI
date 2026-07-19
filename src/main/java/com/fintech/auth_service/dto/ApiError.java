/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.fintech.auth_service.dto;

import java.time.LocalDateTime;

/**
 *
 * @author Harmony
 */
public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    
    public ApiError(
            LocalDateTime timestamp,
            int status,
            String error,
            String message
    ) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
