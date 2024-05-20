package org.example.ticketing.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.point.response.PaymentInfoDTO;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.example.ticketing.infrastructure.external.ExternalPaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class kafkaEventListener {

    private final ExternalPaymentService externalPaymentService;
    private final ReservationService reservationService;

    @KafkaListener(topics = "payment", groupId = "point-payment")
    public void handlePointPaymentEvent(Long reservationId) {
        Optional<Reservation> reservation = reservationService.findById(reservationId);
        if (reservation.isPresent()) {
            PaymentInfoDTO paymentInfoDTO = convertToPaymentInfoDTO(reservation.get());
            try {
                externalPaymentService.sendOrderData(paymentInfoDTO);
            } catch (Exception e) {
                System.err.println("외부 API 호출 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.err.println("예약 정보가 없습니다. reservationId: " + reservationId);
        }
    }

    private PaymentInfoDTO convertToPaymentInfoDTO(Reservation reservation) {
        return new PaymentInfoDTO(
                reservation.getUserId(),
                reservation.getReservationId(),
                reservation.getConcertId(),
                reservation.getSeatId(),
                reservation.getCost(),
                reservation.getStatus(),
                reservation.getReservationTime()
        );
    }
}
