package org.example.ticketing.api.dto.request;

import java.time.LocalDateTime;

public record PointRequestDTO (
        Long userId,
        Long point
){
}
