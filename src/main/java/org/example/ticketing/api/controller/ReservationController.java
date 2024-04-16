package org.example.ticketing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

//    @Operation(summary = "좌석 예약")
//    @PatchMapping("/reservation")
//    public ResponseEntity<ReservationResponseDTO> reserve(@RequestHeader("user_id") Long user_id, @RequestBody ReservationRequestDTO reservationRequestDTO) {
//        UserRequestDTO userRequestDTO = new UserRequestDTO(user_id);
//        return ResponseEntity.status(HttpStatus.OK).body(reservationService.reservationConcert(userRequestDTO, reservationRequestDTO));
//    }
}
