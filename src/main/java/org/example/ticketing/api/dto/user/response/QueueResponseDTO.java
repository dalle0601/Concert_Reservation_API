package org.example.ticketing.api.dto.user.response;

import java.time.LocalDateTime;

public record QueueResponseDTO (
        String message,
        Long waitCount,
        String token,
        LocalDateTime expireTime

){
}
