package org.example.ticketing.api.dto.request;

import java.time.LocalDateTime;

public record PointRequestDTO (
        Long user_id,
        Long reservation_id,
        Long concert_id,
        Long seat_id,
        LocalDateTime payment_time,
        Long cost,
        Long point
){
}
