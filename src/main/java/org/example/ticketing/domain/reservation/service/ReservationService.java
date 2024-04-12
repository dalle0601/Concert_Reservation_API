package org.example.ticketing.domain.reservation.service;

import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final MakeReservationUseCase makeReservationUseCase;

    public ReservationService(MakeReservationUseCase makeReservationUseCase) {
        this.makeReservationUseCase = makeReservationUseCase;
    }

    public ReservationResponseDTO reservationConcert(UserRequestDTO userRequestDTO, ReservationRequestDTO reservationRequestDTO) {
        return makeReservationUseCase.execute(userRequestDTO, reservationRequestDTO);
    }
}
