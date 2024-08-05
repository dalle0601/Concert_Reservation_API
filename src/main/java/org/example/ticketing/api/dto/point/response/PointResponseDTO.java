package org.example.ticketing.api.dto.point.response;

public record PointResponseDTO (
        String message,
        String userId,
        Long point
){
}
