package org.example.ticketing.domain.concert.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name="SEAT")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long seatId;

    @Column(name = "CONCERT_ID")
    private Long concertId;

    @Column(name = "SEAT_NAME")
    private String seatName;

    @Column(name = "SEAT_STATUS")
    private String seatStatus;

    public Seat() {}
    @Builder
    public Seat( Long seat_id, Long concert_id, String seat_name, String seat_status) {
        this.seatId = seat_id;
        this.concertId = concert_id;
        this.seatName = seat_name;
        this.seatStatus = seat_status;
    }
}
