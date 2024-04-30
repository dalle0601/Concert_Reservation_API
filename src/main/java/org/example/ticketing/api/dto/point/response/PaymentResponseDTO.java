package org.example.ticketing.api.dto.point.response;

public record PaymentResponseDTO (
        String message,
        PaymentInfoDTO paymentInfoDTO
){
}
