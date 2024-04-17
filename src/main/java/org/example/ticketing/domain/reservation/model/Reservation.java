package org.example.ticketing.domain.reservation.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="RESERVATION")
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

    @Column(name = "RESERVATION_TIME")
    private LocalDateTime reservationTime;

    @Column(name = "EXPIRATION_TIME")
    private LocalDateTime expirationTime;

    public Reservation() {}
    @Builder
    public Reservation( Long reservation_id, Long user_id, Long concert_id, Long seat_id, String status, LocalDateTime reservation_time, LocalDateTime expiration_time) {
        this.reservationId = reservation_id;
        this.userId = user_id;
        this.concertId = concert_id;
        this.seatId = seat_id;
        this.status = status;
        this.reservationTime = reservation_time;
        this.expirationTime = expiration_time;
    }

    public Reservation(Long user_id, Long concert_id, Long seat_id, LocalDateTime reservation_time, LocalDateTime expiration_time) {
        this.userId = user_id;
        this.concertId = concert_id;
        this.seatId = seat_id;
        this.reservationTime = reservation_time;
        this.expirationTime = expiration_time;
    }
}
