package org.example.ticketing.api.dto.point.reqeust;

public record PaymentRequestDTO (
        Long reservationId,
        String userId
){
}
