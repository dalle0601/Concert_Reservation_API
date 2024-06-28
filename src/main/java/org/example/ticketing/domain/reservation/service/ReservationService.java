package org.example.ticketing.domain.reservation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.point.reqeust.PaymentReservationUpdateDTO;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private static final String RESERVATION_PREFIX = "reservation:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void createTemporaryReservation(ReservationRequestDTO reservationRequestDTO) {
        String key = RESERVATION_PREFIX + reservationRequestDTO.concertId() + ":" + reservationRequestDTO.seatId();
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, reservationRequestDTO, 1, TimeUnit.MINUTES); // 1분 동안 임시 예약 유지
    }

    public List<Reservation> findReservedOrTempSeat(String[] statuses, Long concertId) {
        return reservationRepository.findReservedOrTempSeat(statuses, concertId);
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation findNonAvailableByConcertIdAndSeatId(Long concertId, Long seatId){
        return reservationRepository.findNonAvailableByConcertIdAndSeatId(concertId, seatId);
    }

    public Optional<Reservation> findById(Long reservationId){
        return reservationRepository.findById(reservationId);
    }

    public void updateStateAndExpirationTime(PaymentReservationUpdateDTO paymentReservationUpdateDTO) {
        reservationRepository.updateStateAndExpirationTime(paymentReservationUpdateDTO);
    }

    @Transactional
    public Reservation saveOrUpdate(Reservation reservation) {
        Optional<Reservation> existingReservation = reservationRepository.findByConcertIdAndSeatId(
                reservation.getConcertId(), reservation.getSeatId());

        if (existingReservation.isPresent()) {
            Reservation existing = existingReservation.get();
            existing.setStatus(reservation.getStatus());
            existing.setCost(reservation.getCost());
            existing.setReservationTime(reservation.getReservationTime());
            existing.setExpirationTime(reservation.getExpirationTime());
            return reservationRepository.save(existing);
        } else {
            return reservationRepository.save(reservation);
        }
    }

    public List<Reservation> findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Object[]> findByUserIdAndStatusNotLike(Long userId, String status) {
        return reservationRepository.findByUserIdAndStatusNotLike(userId, status);
    }

    @Transactional
    public void updateReservationStatusToAvailable(Long concertId, Long seatId) {
        reservationRepository.updateStatusToAvailable(concertId, seatId);
    }
}
