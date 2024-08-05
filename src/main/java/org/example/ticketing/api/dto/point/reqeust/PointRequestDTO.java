package org.example.ticketing.api.dto.point.reqeust;

import java.time.LocalDateTime;

public record PointRequestDTO (
        String userId,
        Long point
){
}
