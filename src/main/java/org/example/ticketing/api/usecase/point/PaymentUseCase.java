package org.example.ticketing.api.usecase.point;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.point.reqeust.PaymentRequestDTO;
import org.example.ticketing.api.dto.point.reqeust.PaymentReservationUpdateDTO;
import org.example.ticketing.api.dto.point.reqeust.PointHistorySaveRequestDTO;
import org.example.ticketing.api.dto.point.response.PaymentInfoDTO;
import org.example.ticketing.api.dto.point.response.PaymentResponseDTO;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.example.ticketing.infrastructure.event.PaymentCompletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentUseCase {

    private final ReservationService reservationService;
    private final UserService userService;
    private final ConcertService concertService;
    private final WritePointHistoryUseCase writePointHistoryUseCase;
    private final ApplicationEventPublisher eventPublisher;


    /*
        4단계: 결제 로직에서 윗단계 구현에 대한 로직 구현의 관심사를 분리하기 위해 이벤트를 활용할 것
            - PaymentUseCase에서 이벤트를 발행하고, 이벤트 리스너에서 외부 API 호출을 처리하여 관심사 분리
     */
    @Transactional
    public PaymentResponseDTO execute(PaymentRequestDTO paymentRequestDTO){
        try {
            // reservation table 조회 한다. > reservationId
            // userinfo table 조회한다 > userId
            Optional<Reservation> reservation = reservationService.findById(paymentRequestDTO.reservationId());
            UserInfo userInfo = userService.findUserInfo(new UserRequestDTO(paymentRequestDTO.userId()));

            PaymentResponseDTO validatedPaymentResponseDTO = validation(reservation, userInfo);
            if(validatedPaymentResponseDTO != null) {
                return validatedPaymentResponseDTO;
            }

            LocalDateTime reservationTime = LocalDateTime.now();
            // reservation table update > status => reserved / expireTime = LocalDateTime.max();
            reservationService.updateStateAndExpirationTime(new PaymentReservationUpdateDTO(paymentRequestDTO.reservationId(),"reserved", reservationTime, LocalDateTime.MAX));

            // userInfo table update > point => userInfo.point - reservaion.cost
            Long updatePoint = userInfo.getPoint() - reservation.get().getCost();
            userService.paymentPoint(paymentRequestDTO.userId(), updatePoint);

            // pointHistory 내용 insert
            writePointHistoryUseCase.execute(new PointHistorySaveRequestDTO(paymentRequestDTO.userId(), reservation.get().getCost(), "payment"));

            Reservation reservationInfo = reservation.get();
            PaymentInfoDTO paymentInfoDTO = new PaymentInfoDTO(
                    userInfo.getUserId(),
                    reservationInfo.getReservationId(),
                    reservationInfo.getConcertId(),
                    reservationInfo.getSeatId(),
                    reservationInfo.getCost(),
                    "reserved",
                    reservationTime);

            eventPublisher.publishEvent(new PaymentCompletedEvent(reservationInfo.getReservationId()));
//            paymentEventProducer.send("payment", reservationInfo.getReservationId());

            return new PaymentResponseDTO("결제 완료", paymentInfoDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new PaymentResponseDTO("결제 중 오류가 발생했습니다.", null);
        }
    }

    private PaymentResponseDTO validation(Optional<Reservation> reservation, UserInfo userInfo) {
        if(reservation.isEmpty()) {
            return new PaymentResponseDTO("예약 정보가 없습니다.", null);
        }
        if(userInfo == null) {
            return new PaymentResponseDTO("사용자 정보가 없습니다.", null);
        }
        Concert concert = concertService.findByConcertId(reservation.get().getConcertId());
        if(concert == null) {
            return new PaymentResponseDTO("해당 콘서트 정보가 없습니다.", null);
        }
        if(userInfo.getPoint() < reservation.get().getCost()) {
            return new PaymentResponseDTO("포인트가 부족합니다.", null);
        }
        return null;
    }
}
