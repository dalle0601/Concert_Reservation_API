package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.api.dto.request.SeatReqeustDTO;
import org.example.ticketing.api.dto.response.SeatResponseDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.model.Seat;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ConcertRepositoryImpl implements ConcertRepository{
    private final ConcertJpaRepository concertJpaRepository;

    public ConcertRepositoryImpl (ConcertJpaRepository concertJpaRepository){
        this.concertJpaRepository = concertJpaRepository;
    }
    @Override
    public List<Concert> getConcertDateByToday(LocalDateTime today) {
        return concertJpaRepository.findByAvailableStartDate(LocalDateTime.now());
    }

    @Override
    public List<Seat> getConcertSeatById(Long concert_id) {
        return concertJpaRepository.findByAvailableSeat(concert_id);
    }
}
