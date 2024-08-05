package org.example.ticketing.api.dto.point.reqeust;

public record PointHistorySaveRequestDTO(
        String userId,
        Long point,
        String status
) {
}
