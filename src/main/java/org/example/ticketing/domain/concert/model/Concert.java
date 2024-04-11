package org.example.ticketing.domain.reservation.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="CONCERT")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long concertId;

    @Column(name = "CONCERT_TITLE")
    private String concertTitle;

    @Column(name = "CONCERT_DATE")
    private LocalDateTime concertDate;

    @Column(name = "MAX_SEAT_CNT")
    private Long maxSeatCnt;

    @Column(name = "AVAILABLE_SEAT_CNT")
    private Long availableSeatCnt;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
