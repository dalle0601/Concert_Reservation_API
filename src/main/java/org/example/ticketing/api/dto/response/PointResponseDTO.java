package org.example.ticketing.api.dto.response;

public record PointResponseDTO (
        String message,
        Long userId,
        Long point
){
}
