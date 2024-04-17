package org.example.ticketing.domain.reservation.service;

import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
//    public ReservationResponseDTO reservationConcert(UserRequestDTO userRequestDTO, ReservationRequestDTO reservationRequestDTO) {
//        return makeReservationUseCase.execute(userRequestDTO, reservationRequestDTO);
//    }

    public List<Reservation> findReservedOrTempSeat(String[] statuses, Long concertId) {
        return reservationRepository.findReservedOrTempSeat(statuses, concertId);
    }
}
