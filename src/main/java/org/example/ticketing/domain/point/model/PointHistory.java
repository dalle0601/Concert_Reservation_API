package org.example.ticketing.domain.point.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="POINT")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long pointId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "POINT")
    private Long point;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public PointHistory() {}
    @Builder
    public PointHistory( Long point_id, Long user_id, Long point, String status, LocalDateTime created_at) {
        this.pointId = point_id;
        this.userId = user_id;
        this.point = point;
        this.status = status;
        this.createdAt = created_at;
    }
}
