package org.example.ticketing.api.dto.reservation.response;

import org.example.ticketing.api.dto.user.response.UserReservationResponseDTO;

import java.util.List;

public record ReservationListResponseDTO(
        String message,
        List<UserReservationResponseDTO> list
) {
}
