package org.example.ticketing.domain.reservation.repository;

import org.example.ticketing.api.dto.request.PaymentReservationUpdateDTO;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository{
    private final ReservationJpaRepository reservationJpaRepository;
    public ReservationRepositoryImpl (ReservationJpaRepository reservationJpaRepository){
        this.reservationJpaRepository = reservationJpaRepository;
    }
    @Override
    public List<Reservation> findReservedOrTempSeat(String[] statuses, Long concertId) {
        return reservationJpaRepository.findByStatusInAndConcertId(statuses, concertId);
    }
    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(reservation);
    }

    @Override
    public Reservation findNonAvailableByConcertIdAndSeatId(Long concertId, Long seatId) {
        return  reservationJpaRepository.findNonAvailableByConcertIdAndSeatId(concertId, seatId);
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return reservationJpaRepository.findById(reservationId);
    }

    @Override
    public void updateStateAndExpirationTime(PaymentReservationUpdateDTO paymentReservationUpdateDTO) {
        reservationJpaRepository.updateStateAndExpirationTime(
                paymentReservationUpdateDTO.reserationId(),
                paymentReservationUpdateDTO.status(),
                paymentReservationUpdateDTO.reservationTime(),
                paymentReservationUpdateDTO.expiredTime()
        );
    }
}
