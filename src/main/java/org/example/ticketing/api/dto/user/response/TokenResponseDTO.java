package org.example.ticketing.api.dto.user.response;

public record TokenResponseDTO (
        String message,
        String token,
        String expiredTime
){
}
