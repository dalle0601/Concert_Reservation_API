package org.example.ticketing.api.dto.reservation.request;

import java.time.LocalDateTime;

public record ReservationRequestDTO (
        Long concertId,
        Long seatId,
        String userId,
        Long cost
){
}
