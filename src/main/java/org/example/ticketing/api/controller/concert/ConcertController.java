package org.example.ticketing.api.controller.concert;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.concert.response.ConcertResponseDTO;
import org.example.ticketing.api.dto.common.Response;
import org.example.ticketing.api.dto.concert.response.SeatResponseDTO;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableDateUseCase;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableSeatUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConcertController {
    private final GetConcertAvailableDateUseCase getConcertAvailableDateUseCase;
    private final GetConcertAvailableSeatUseCase getConcertAvailableSeatUseCase;

    public ConcertController(GetConcertAvailableDateUseCase getConcertAvailableDateUseCase, GetConcertAvailableSeatUseCase getConcertAvailableSeatUseCase) {
        this.getConcertAvailableDateUseCase = getConcertAvailableDateUseCase;
        this.getConcertAvailableSeatUseCase = getConcertAvailableSeatUseCase;
    }

    @Operation(summary = "예약가능한 콘서트 날짜 조회")
    @GetMapping("/concert/date")
    public Response<ConcertResponseDTO> getConcertDate(@RequestHeader("userId") Long userId) throws Exception {
        return Response.success(getConcertAvailableDateUseCase.execute(new UserRequestDTO(userId)));

    }

    @Operation(summary = "예약가능한 콘서트 좌석 조회")
    @GetMapping("/concert/{concertId}/seat")
    public Response<SeatResponseDTO> getConcertSeat(@RequestHeader("userId") Long userId, @PathVariable("concertId") Long concertId) throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        return Response.success(getConcertAvailableSeatUseCase.execute(userRequestDTO, concertId));
    }

}
