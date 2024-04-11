package org.example.ticketing.domain.concert.service;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableDateUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {
    private final GetConcertAvailableDateUseCase getConcertAvailableDateUseCase;

    public ConcertService(GetConcertAvailableDateUseCase getConcertAvailableDateUseCase) {
        this.getConcertAvailableDateUseCase = getConcertAvailableDateUseCase;
    }

    public List<Concert> getConcertDate(UserRequestDTO userRequestDTO) {
        return getConcertAvailableDateUseCase.execute(userRequestDTO);
    }

}
