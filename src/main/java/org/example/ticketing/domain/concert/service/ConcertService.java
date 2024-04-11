package org.example.ticketing.domain.concert.service;

import org.example.ticketing.api.dto.request.SeatReqeustDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableDateUseCase;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableSeatUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.model.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {
    private final GetConcertAvailableDateUseCase getConcertAvailableDateUseCase;
    private final GetConcertAvailableSeatUseCase getConcertAvailableSeatUseCase;

    public ConcertService(GetConcertAvailableDateUseCase getConcertAvailableDateUseCase, GetConcertAvailableSeatUseCase getConcertAvailableSeatUseCase) {
        this.getConcertAvailableDateUseCase = getConcertAvailableDateUseCase;
        this.getConcertAvailableSeatUseCase = getConcertAvailableSeatUseCase;
    }

    public List<Concert> getConcertDate(UserRequestDTO userRequestDTO) {
        return getConcertAvailableDateUseCase.execute(userRequestDTO);
    }

    public List<Seat> getConcertSeat(UserRequestDTO userRequestDTO, SeatReqeustDTO seatReqeustDTO) {
        return getConcertAvailableSeatUseCase.execute(userRequestDTO, seatReqeustDTO);
    }
}
