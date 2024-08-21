package org.example.ticketing.api.dto.user.response;

public record LoginResponseDTO (
        String userId,
        String accessToken,
        String refreshToken
){
}
