package org.example.ticketing.domain.concert.service;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.concert.response.ConcertWithSeatCountDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;

    public List<ConcertWithSeatCountDTO> getConcertDate(LocalDateTime currentDate) {
        return concertRepository.getConcertDateByToday(currentDate);
    }
    public Concert findByConcertId(Long concertId) {
        return concertRepository.findByConcertId(concertId);
    }
}
