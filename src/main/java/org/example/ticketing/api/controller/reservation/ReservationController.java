package org.example.ticketing.api.controller.reservation;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final MakeReservationUseCase makeReservationUseCase;

    @Autowired
    public ReservationController(MakeReservationUseCase makeReservationUseCase) {
        this.makeReservationUseCase = makeReservationUseCase;
    }

    @Operation(summary = "좌석 예약")
    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDTO> reserve(@RequestBody ReservationRequestDTO reservationRequestDTO) throws InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(makeReservationUseCase.execute(reservationRequestDTO));
    }
}
