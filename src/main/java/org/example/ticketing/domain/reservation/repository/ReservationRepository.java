package org.example.ticketing.domain.reservation.repository;

import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.reservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findReservedOrTempSeat(String[] statuses, Long concertId);
    Reservation save(Reservation reservation);
    Reservation findNonAvailableByConcertIdAndSeatId(Long concertId, Long seatId);
}
