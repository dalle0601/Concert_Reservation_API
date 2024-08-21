package org.example.ticketing.api.controller.reservation;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.common.Response;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response<ReservationResponseDTO> reserve(@RequestBody ReservationRequestDTO reservationRequestDTO) throws InterruptedException {
        try {
            ReservationResponseDTO result = makeReservationUseCase.execute(reservationRequestDTO);
            return Response.success(result);
        } catch (RuntimeException e) {
            // 예외 메시지에 따라 적절한 에러 코드를 반환
            return switch (e.getMessage()) {
                case "LOCK_FAILED" -> Response.error("LOCK_FAILED"); // 락 실패
                case "DATABASE_ERROR" -> Response.error("DATABASE_ERROR"); // 데이터베이스 오류
                case "GENERAL_ERROR" -> Response.error("GENERAL_ERROR"); // 일반 오류
                default -> Response.error("UNKNOWN_ERROR"); // 기타 오류
            };
        }
    }
}
