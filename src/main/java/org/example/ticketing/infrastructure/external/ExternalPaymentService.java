package org.example.ticketing.infrastructure.external;

import org.example.ticketing.api.dto.point.response.PaymentInfoDTO;

public interface ExternalPaymentService {
    void sendOrderData(PaymentInfoDTO paymentInfoDTO);
}