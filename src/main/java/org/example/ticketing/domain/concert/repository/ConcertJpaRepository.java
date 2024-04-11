package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.domain.concert.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
    @Query("SELECT c FROM Concert c WHERE c.concertDate >= :concertDate AND c.availableSeatCnt > 0")
    List<Concert> findByStartDate(@Param("concertDate") LocalDateTime concertDate);
}
