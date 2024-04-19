package org.example.ticketing.api.dto.response;

import org.example.ticketing.domain.reservation.model.Reservation;

public record ReservationResponseDTO(
        String message,
        Reservation reservation
) {
}
