package org.example.ticketing.api.dto.request;

public record PointHistorySaveRequestDTO(
        Long userId,
        Long point,
        String status
) {
}
