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
public class PaymentEventListener {

    /*
        1단계: 외부 API 호출의 실패에 트랜잭션이 영향받지 않게 할 것
        2단계: 외부 API 호출의 지연에 트랜잭션의 길이가 영향받지 않게 할 것
            - 이벤트 리스너를 비동기로 처리하여 외부 API 호출의 지연이 트랜잭션 길이에 영향을 주지 않도록
        3단계: 외부 API 호출의 지연에 사용자의 주문 성공 응답 시간에 영향받지 않게 할 것
            - 비동기 이벤트 리스너를 사용하여 외부 API 호출의 지연이 사용자 응답 시간에 영향을 주지 않도록
     */
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
