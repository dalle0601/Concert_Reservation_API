package org.example.ticketing.domain.reservation.repository;

import org.example.ticketing.api.dto.point.reqeust.PaymentReservationUpdateDTO;
import org.example.ticketing.domain.reservation.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<Reservation> findReservedOrTempSeat(String[] statuses, Long concertId);
    Reservation save(Reservation reservation);
    Reservation findNonAvailableByConcertIdAndSeatId(Long concertId, Long seatId);
    Optional<Reservation> findById(Long reservationId);
    Optional<Reservation> findByConcertIdAndSeatId(Long concertId, Long seatId);
    void updateStateAndExpirationTime(PaymentReservationUpdateDTO paymentReservationUpdateDTO);
    List<Reservation> findByUserId(String userId);
    List<Object[]> findByUserIdAndStatusNotLike(String userId, String status);
    void updateStatusToAvailable(Long concertId, Long seatId);
}
