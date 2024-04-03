package org.example.ticketing.api.dto.request;

import java.time.LocalDateTime;

public record ReservationRequestDTO (
        Long concert_id,
        Long seat_id,
        String user_id,
        Long cost,
        LocalDateTime reservation_time
){
}
