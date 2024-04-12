package org.example.ticketing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.request.SeatReqeustDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConcertController {
    private final ConcertService concertService;

    @Autowired
    public ConcertController (ConcertService concertService){
        this.concertService = concertService;
    }
    @Operation(summary = "예약가능한 콘서트 날짜 조회")
    @GetMapping("/concert/date")
    public ResponseEntity<List<Concert>> getConcertDate(@RequestHeader("user_id") Long user_id) {
        UserRequestDTO userRequestDTO = new UserRequestDTO(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(concertService.getConcertDate(userRequestDTO));
    }
    @Operation(summary = "예약가능한 콘서트 좌석 조회")
    @GetMapping("/concert/{concert_id}/seat")
    public ResponseEntity<List<Seat>> getConcertSeat(@RequestHeader("user_id") Long user_id, @PathVariable("concert_id") Long concert_id) {
        UserRequestDTO userRequestDTO = new UserRequestDTO(user_id);
        SeatReqeustDTO seatReqeustDTO = new SeatReqeustDTO(concert_id);
        return ResponseEntity.status(HttpStatus.OK).body(concertService.getConcertSeat(userRequestDTO, seatReqeustDTO));
    }

}
