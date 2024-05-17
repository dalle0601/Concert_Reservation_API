package org.example.ticketing.domain.reservation.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.point.reqeust.PaymentReservationUpdateDTO;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

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
}
