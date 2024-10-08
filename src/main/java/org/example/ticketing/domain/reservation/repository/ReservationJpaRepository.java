package org.example.ticketing.domain.reservation.repository;

import org.example.ticketing.domain.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatusInAndConcertId(String[] statuses, Long concertId);

    @Query("SELECT r FROM Reservation r WHERE r.concertId = :concertId AND r.seatId = :seatId AND r.status <> 'available'")
    Reservation findNonAvailableByConcertIdAndSeatId(@Param("concertId") Long concertId, @Param("seatId") Long seatId);
    @Modifying
    @Query("UPDATE Reservation r SET r.status = :status, r.reservationTime = :reservationTime, r.expirationTime = :expirationTime WHERE r.reservationId = :reservationId")
    void updateStateAndExpirationTime(@Param("reservationId") Long reservationId, @Param("status") String status, @Param("reservationTime") LocalDateTime reservationTime, @Param("expirationTime") LocalDateTime expirationTime);
    Optional<Reservation> findByConcertIdAndSeatId(Long concertId, Long seatId);
    Reservation save(Reservation reservation);

    List<Reservation> findByUserId(String userId);

    @Query("SELECT r.reservationId, r.userId, r.concertId, r.seatId, r.status, r.cost, r.reservationTime, r.expirationTime, c.concertTitle, c.imagePath " +
            "FROM Reservation r " +
            "JOIN Concert c ON r.concertId = c.concertId " +
            "WHERE r.userId = :userId AND r.status <> :status")
    List<Object[]> findByUserIdAndStatusNotLike(@Param("userId") String userId, @Param("status") String status);

    @Modifying
    @Query("UPDATE Reservation r SET r.status = 'available' WHERE r.concertId = :concertId AND r.seatId = :seatId AND r.status = 'temporary'")
    void updateStatusToAvailable(@Param("concertId") Long concertId, @Param("seatId") Long seatId);
}
