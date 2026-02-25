package com.example.authservice.dto;

import java.util.UUID;

public record UserInfoResponse(
        UUID userId,
        String email,
        String firstName,
        String lastName,
        String role,
        boolean active
) {
}
