package org.example.ticketing.api.dto.response;

import org.example.ticketing.domain.reservation.model.Reservation;

import java.time.LocalDateTime;

public record ReservationResponseDTO(
        String message,
        Reservation reservation
) {
}
