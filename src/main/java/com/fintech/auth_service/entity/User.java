/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.fintech.auth_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

/**
 *
 * @author Harmony
 */
@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    private String fullName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
    this.createdAt = LocalDateTime.now();
}
}
