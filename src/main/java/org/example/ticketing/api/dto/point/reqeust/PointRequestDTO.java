package org.example.ticketing.api.dto.point.reqeust;

import java.time.LocalDateTime;

public record PointRequestDTO (
        Long userId,
        Long point
){
}
