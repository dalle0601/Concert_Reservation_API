package org.example.ticketing.domain.reservation.repository;

import org.example.ticketing.domain.reservation.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository{
    private final ReservationJpaRepository reservationJpaRepository;

    public ReservationRepositoryImpl (ReservationJpaRepository reservationJpaRepository){
        this.reservationJpaRepository = reservationJpaRepository;
    }
    @Override
    public Reservation reservationConcert(Reservation reservation) {
        return reservationJpaRepository.save(reservation);
    }

    @Override
    public List<Reservation> findReservedOrTempSeat(String[] statuses, Long concertId) {
        return reservationJpaRepository.findByStatusInAndConcertId(statuses, concertId);
    }
}
