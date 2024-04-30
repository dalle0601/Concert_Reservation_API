package org.example.ticketing.api.dto.point.reqeust;

public record PointHistorySaveRequestDTO(
        Long userId,
        Long point,
        String status
) {
}
