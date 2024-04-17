package org.example.ticketing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.request.SeatReqeustDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ConcertResponseDTO;
import org.example.ticketing.api.dto.response.SeatResponseDTO;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableDateUseCase;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableSeatUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ConcertResponseDTO> getConcertDate(@RequestHeader("user_id") Long user_id) throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(getConcertAvailableDateUseCase.execute(userRequestDTO));
    }
    @Operation(summary = "예약가능한 콘서트 좌석 조회")
    @GetMapping("/concert/{concert_id}/seat")
    public ResponseEntity<SeatResponseDTO> getConcertSeat(@RequestHeader("user_id") Long user_id, @PathVariable("concert_id") Long concert_id) throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(getConcertAvailableSeatUseCase.execute(userRequestDTO, concert_id));
    }

}
