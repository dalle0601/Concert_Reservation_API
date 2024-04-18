package org.example.ticketing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    private final MakeReservationUseCase makeReservationUseCase;
    @Autowired
    public ReservationController(MakeReservationUseCase makeReservationUseCase) {
        this.makeReservationUseCase = makeReservationUseCase;
    }

    @Operation(summary = "좌석 예약")
    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDTO> reserve(@RequestHeader("userId") Long userId, @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(makeReservationUseCase.execute(reservationRequestDTO));
    }
}
