package org.example.ticketing.api.dto.response;

import org.example.ticketing.domain.reservation.model.Reservation;

import java.time.LocalDateTime;

public record ReservationResponseDTO(
        Long user_id,
        Long concert_id,
        Long seat_id,
        Long cost,
        String seat_status,
        LocalDateTime reservation_time,
        LocalDateTime reservation_deadline

) {
}
