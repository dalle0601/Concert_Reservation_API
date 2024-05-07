package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.api.dto.concert.response.ConcertWithSeatCountDTO;
import org.example.ticketing.domain.concert.model.Concert;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository {
    List<ConcertWithSeatCountDTO> getConcertDateByToday(LocalDateTime today);
//    List<Seat> getConcertSeatById(Long concert_id);
    Concert findByConcertId(Long concertId);
}
