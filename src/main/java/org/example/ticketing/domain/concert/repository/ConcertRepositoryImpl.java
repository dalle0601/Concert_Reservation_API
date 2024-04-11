package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.domain.concert.model.Concert;
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
    public List<Concert> getConcertByDate(LocalDateTime today) {
        return concertJpaRepository.findByStartDate(LocalDateTime.now());
    }
}
