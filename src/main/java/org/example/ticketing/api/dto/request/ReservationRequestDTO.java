package org.example.ticketing.api.dto.request;

import java.time.LocalDateTime;

public record ReservationRequestDTO (
        Long concertId,
        Long seatId,
        Long userId,
        Long cost
){
}
