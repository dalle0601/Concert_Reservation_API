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

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    public Concert() {

    }
    @Builder
    public Concert( Long concert_id, String concert_title, LocalDateTime concert_date, String imagePath, LocalDateTime created_at) {
        this.concertId = concert_id;
        this.concertTitle = concert_title;
        this.concertDate = concert_date;
        this.imagePath = imagePath;
        this.createdAt = created_at;
    }
}
