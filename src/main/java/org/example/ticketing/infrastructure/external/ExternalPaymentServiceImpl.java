package org.example.ticketing.infrastructure.external;

import org.example.ticketing.api.dto.point.response.PaymentInfoDTO;
import org.springframework.stereotype.Service;

@Service
public class ExternalPaymentServiceImpl implements ExternalPaymentService {
    /*
        0단계: 기존 로직으로 결제 데이터를 전달할 것 (mock API 등으로 구현)
        ExternalPaymentService
     */
    @Override
    public void sendOrderData(PaymentInfoDTO paymentInfoDTO) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
        System.out.println("Mock API 호출: " + paymentInfoDTO);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
    }
}