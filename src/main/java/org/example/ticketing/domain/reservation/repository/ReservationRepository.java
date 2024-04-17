package org.example.ticketing.domain.reservation.repository;

import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.reservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    Reservation reservationConcert(Reservation reservation);

    List<Reservation> findReservedOrTempSeat(String[] statuses, Long concertId);
}
