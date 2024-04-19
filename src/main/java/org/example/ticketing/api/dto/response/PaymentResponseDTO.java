package org.example.ticketing.api.dto.response;

public record PaymentResponseDTO (
        String message,
        PaymentInfoDTO paymentInfoDTO
){
}
