package org.example.ticketing.api.dto.point.reqeust;

import java.time.LocalDateTime;

public record PaymentReservationUpdateDTO (
        Long reserationId,
        String status,
        LocalDateTime reservationTime,
        LocalDateTime expiredTime
){
}
