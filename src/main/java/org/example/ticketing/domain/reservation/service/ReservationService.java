package org.example.ticketing.domain.reservation.service;


import jakarta.persistence.EntityManager;
import org.example.ticketing.api.dto.request.PaymentReservationUpdateDTO;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, EntityManager entityManager) {
        this.reservationRepository = reservationRepository;
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
}
