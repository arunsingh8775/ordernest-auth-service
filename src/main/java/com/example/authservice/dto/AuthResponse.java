package com.example.authservice.dto;

import java.util.UUID;

public class AuthResponse {
    private final String token;
    private final UUID userId;
    private final String role;

    public AuthResponse(String token, UUID userId, String role) {
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
