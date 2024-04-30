package org.example.ticketing.api.dto.point.response;

public record PointResponseDTO (
        String message,
        Long userId,
        Long point
){
}
