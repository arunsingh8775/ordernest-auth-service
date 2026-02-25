package com.example.authservice.dto;

public record UserInfoResponse(
        String email,
        String firstName,
        String lastName
) {
}
