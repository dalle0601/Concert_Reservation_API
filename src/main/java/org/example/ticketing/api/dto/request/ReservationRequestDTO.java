package org.example.ticketing.api.dto.request;

import java.time.LocalDateTime;

public record ReservationRequestDTO (
        Long concert_id,
        Long seat_id,
        Long user_id,
        LocalDateTime reservation_time,
        LocalDateTime expiration_time
){
}
