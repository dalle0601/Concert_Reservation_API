package org.example.ticketing.api.dto.response;

import org.example.ticketing.domain.point.model.PointHistory;

public record PointHistorySaveResponseDTO(
        String message,
        PointHistory pointHistory
) {
}
