package org.example.ticketing.api.dto.request;

import java.time.LocalDateTime;

public record PaymentReservationUpdateDTO (
        Long reserationId,
        String status,
        LocalDateTime reservationTime,
        LocalDateTime expiredTime
){
}
