package org.example.ticketing.api.dto.response;

import java.time.LocalDateTime;

public record ReservationResponseDTO(
        Long concert_id,
        String user_id,
        String concert_name,
        String seat_number,
        Long cost,
        String seat_status,
        LocalDateTime reservation_time,
        LocalDateTime reservation_deadline

){
}
