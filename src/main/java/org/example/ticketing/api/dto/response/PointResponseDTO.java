package org.example.ticketing.api.dto.response;

import java.time.LocalDateTime;

public record PointResponseDTO (
        String message,
        Long userId,
        Long point
){
}
