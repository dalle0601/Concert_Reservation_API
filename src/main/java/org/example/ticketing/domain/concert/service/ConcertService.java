package org.example.ticketing.domain.concert.service;

import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConcertService {
    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }
    public List<Concert> getConcertDate(LocalDateTime currentDate) {
        return concertRepository.getConcertDateByToday(currentDate);
    }
    public Concert findByConcertId(Long concertId) {
        return concertRepository.findByConcertId(concertId);
    }
}
