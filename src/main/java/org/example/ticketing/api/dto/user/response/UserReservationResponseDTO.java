package org.example.ticketing.api.dto.user.response;

import java.time.LocalDateTime;

public record UserReservationResponseDTO(
        Long reservationId,
        Long userId,
        Long concertId,
        Long seatId,
        String seatName,
        String status,
        Long cost,
        LocalDateTime reservationTime,
        LocalDateTime expirationTime,
        String concertTitle,
        String imagePath
) {
}
