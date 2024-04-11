package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.api.dto.response.SeatResponseDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
    @Query("SELECT c FROM Concert c WHERE c.concertDate >= :concertDate AND c.availableSeatCnt > 0")
    List<Concert> findByAvailableStartDate(@Param("concertDate") LocalDateTime concertDate);
    @Query("SELECT s FROM Seat s WHERE s.concertId = :concertId AND s.seatStatus != 'reserved'")
    List<Seat> findByAvailableSeat(@Param("concertId") Long concertId);
}
