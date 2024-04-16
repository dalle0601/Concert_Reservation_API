package org.example.ticketing.api.dto.response;

import java.time.LocalDateTime;

public record QueueResponseDTO (
        String message,
        Long waitCount,
        String token,
        LocalDateTime expireTime

){
}
