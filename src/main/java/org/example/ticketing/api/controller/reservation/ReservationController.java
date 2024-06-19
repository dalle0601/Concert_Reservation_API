package org.example.ticketing.api.controller.reservation;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.common.Response;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationListResponseDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.api.usecase.reservation.UserReservationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    private final MakeReservationUseCase makeReservationUseCase;
    private final UserReservationUseCase userReservationUseCase;

    @Autowired
    public ReservationController(MakeReservationUseCase makeReservationUseCase, UserReservationUseCase userReservationUseCase) {
        this.makeReservationUseCase = makeReservationUseCase;
        this.userReservationUseCase = userReservationUseCase;
    }

    @Operation(summary = "좌석 예약")
    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDTO> reserve(@RequestBody ReservationRequestDTO reservationRequestDTO) throws InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(makeReservationUseCase.execute(reservationRequestDTO));
    }

    @Operation(summary = "유저 예약 내역 요청")
    @GetMapping("/user/{userId}/reservations")
    public Response<ReservationListResponseDTO> getUserReservation (@PathVariable Long userId) throws Exception {
        return Response.success(userReservationUseCase.execute(new UserRequestDTO(userId)));
    }
}
