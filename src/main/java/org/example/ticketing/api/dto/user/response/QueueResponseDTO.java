package org.example.ticketing.api.dto.user.response;

public record QueueResponseDTO (
        String message,
        Long waitCount,
        String expireTime
){
}
