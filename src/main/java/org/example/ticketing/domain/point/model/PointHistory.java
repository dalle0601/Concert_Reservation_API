package org.example.ticketing.domain.point.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

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
    private String userId;

    @Column(name = "POINT")
    private Long point;

    @Column(name = "STATUS")
    private String status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public PointHistory(Long pointId, String userId, Long point, String status, LocalDateTime createdAt) {
        this.pointId = pointId;
        this.userId = userId;
        this.point = point;
        this.status = status;
        this.createdAt = createdAt;
    }
    public PointHistory(String userId, Long point, String status) {
        this.userId = userId;
        this.point = point;
        this.status = status;
    }

    public PointHistory() {

    }
}
