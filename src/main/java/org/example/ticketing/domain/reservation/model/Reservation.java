package org.example.ticketing.domain.reservation.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="RESERVATION"
//        , uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"CONCERT_ID", "SEAT_ID"})
//        }
)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long reservationId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CONCERT_ID")
    private Long concertId;

    @Column(name = "SEAT_ID")
    private Long seatId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "COST")
    private Long cost;

    @Column(name = "RESERVATION_TIME")
    private LocalDateTime reservationTime;

    @Column(name = "EXPIRATION_TIME")
    private LocalDateTime expirationTime;

    public Reservation() {}
    @Builder
    public Reservation( Long reservation_id, Long userId, Long concertId, Long seatId, String status, Long cost, LocalDateTime reservationTime, LocalDateTime expirationTime) {
        this.reservationId = reservation_id;
        this.userId = userId;
        this.concertId = concertId;
        this.seatId = seatId;
        this.status = status;
        this.cost = cost;
        this.reservationTime = reservationTime;
        this.expirationTime = expirationTime;
    }

    public Reservation(Long userId, Long concertId, Long seatId, String status, Long cost, LocalDateTime reservationTime, LocalDateTime expirationTime) {
        this.userId = userId;
        this.concertId = concertId;
        this.seatId = seatId;
        this.status = status;
        this.cost = cost;
        this.reservationTime = reservationTime;
        this.expirationTime = expirationTime;
    }
}
