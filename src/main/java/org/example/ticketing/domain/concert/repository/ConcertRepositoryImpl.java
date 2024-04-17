package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.user.model.Token;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public List<Concert> findByConcertId(Long concertId) {
        return concertJpaRepository.findByConcertId(concertId);
    }

//    @Override
//    public List<Seat> getConcertSeatById(Long concert_id) {
//        return concertJpaRepository.findByAvailableSeat(concert_id);
//    }

}
