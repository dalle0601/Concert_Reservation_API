package org.example.ticketing.domain.reservation.repository;

import org.example.ticketing.domain.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatusInAndConcertId(String[] statuses, Long concertId);

    @Query("SELECT r FROM Reservation r WHERE r.concertId = :concertId AND r.seatId = :seatId AND r.status <> 'available'")
    Reservation findNonAvailableByConcertIdAndSeatId(@Param("concertId") Long concertId, @Param("seatId") Long seatId);
    @Modifying
    @Query("UPDATE Reservation r SET r.status = :status, r.reservationTime = :reservationTime, r.expirationTime = :expirationTime WHERE r.reservationId = :reservationId")
    void updateStateAndExpirationTime(@Param("reservationId") Long reservationId, @Param("status") String status, @Param("reservationTime") LocalDateTime reservationTime, @Param("expirationTime") LocalDateTime expirationTime);

    Reservation save(Reservation reservation);
}
