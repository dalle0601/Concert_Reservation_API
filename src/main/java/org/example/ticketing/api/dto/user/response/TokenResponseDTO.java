package org.example.ticketing.api.dto.user.response;

import java.time.LocalDateTime;

public record TokenResponseDTO (
        String message,
        String token,
        LocalDateTime expiredTime
){
}
