package org.example.ticketing.domain.reservation.repository;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.point.reqeust.PaymentReservationUpdateDTO;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository{
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public List<Reservation> findReservedOrTempSeat(String[] statuses, Long concertId) {
        return reservationJpaRepository.findByStatusInAndConcertId(statuses, concertId);
    }
    @Override
    public Optional<Reservation> findByConcertIdAndSeatId(Long concertId, Long seatId) {
        return reservationJpaRepository.findByConcertIdAndSeatId(concertId, seatId);
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
    @Override
    public List<Reservation> findByUserId(Long userId) {
        return reservationJpaRepository.findByUserId(userId);
    }

    @Override
    public List<Reservation> findByUserIdAndStatusNotLike(Long userId, String status) {
        return reservationJpaRepository.findByUserIdAndStatusNotLike(userId, status);
    }

    @Override
    public void updateStatusToAvailable(Long concertId, Long seatId) {
        reservationJpaRepository.updateStatusToAvailable(concertId, seatId);
    }
}
