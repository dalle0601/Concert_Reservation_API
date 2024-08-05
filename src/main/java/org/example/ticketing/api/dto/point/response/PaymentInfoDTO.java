package org.example.ticketing.api.dto.point.response;

import java.time.LocalDateTime;

public record PaymentInfoDTO (
        String userId,
        Long reservationId,
        Long concertId,
        Long seatId,
        Long cost,
        String status,
        LocalDateTime paymentTime
){
}
