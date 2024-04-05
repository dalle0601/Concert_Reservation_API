package org.example.ticketing.api.dto.response;

import java.time.LocalDateTime;

public record ReservationResponseDTO(
        Long reservation_id,
        Long concert_id,
        Long user_id,
        Long seat_id,
        Long cost,
        String seat_status,
        LocalDateTime reservation_time,
        LocalDateTime reservation_deadline

){
}
