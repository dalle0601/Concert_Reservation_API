package org.example.ticketing.api.dto.request;

public record PaymentRequestDTO (
        Long reservationId,
        Long userId
){
}
