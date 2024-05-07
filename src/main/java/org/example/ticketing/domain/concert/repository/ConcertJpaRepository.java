package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.api.dto.concert.response.ConcertWithSeatCountDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
    @Query("SELECT new org.example.ticketing.api.dto.concert.response.ConcertWithSeatCountDTO(c, 50 - COUNT(r)) " +
            "FROM Concert c " +
            "LEFT JOIN Reservation r ON c.concertId = r.concertId AND r.status <> 'available' " +
            "WHERE c.concertDate >= :concertDate " +
            "GROUP BY c.concertId")
    List<ConcertWithSeatCountDTO> findByAvailableStartDate(@Param("concertDate") LocalDateTime concertDate);
    Concert findByConcertId(@Param("concertId") Long concertId);
}
