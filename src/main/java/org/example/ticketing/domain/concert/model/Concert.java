package org.example.ticketing.domain.concert.model;

import jakarta.persistence.*;
import lombok.Builder;
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
    public Concert() {

    }
    @Builder
    public Concert( Long concert_id, String concert_title, LocalDateTime concert_date, Long max_seat_cnt, Long available_seat_cnt, LocalDateTime created_at) {
        this.concertId = concert_id;
        this.concertTitle = concert_title;
        this.concertDate = concert_date;
        this.maxSeatCnt = max_seat_cnt;
        this.availableSeatCnt = available_seat_cnt;
        this.createdAt = created_at;
    }


}
