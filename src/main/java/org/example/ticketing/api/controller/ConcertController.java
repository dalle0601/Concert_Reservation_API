package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConcertController {
    private final ConcertService concertService;

    @Autowired
    public ConcertController (ConcertService concertService){
        this.concertService = concertService;
    }

    @GetMapping("/concert/date")
    public ResponseEntity<List<Concert>> getConcertDate(@RequestHeader("user_id") Long user_id) {
        UserRequestDTO userRequestDTO = new UserRequestDTO(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(concertService.getConcertDate(userRequestDTO));
    }

}
