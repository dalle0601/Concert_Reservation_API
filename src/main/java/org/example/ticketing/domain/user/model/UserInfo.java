package org.example.ticketing.domain.user.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="USER_INFO", indexes = {
        @Index(name ="idx_user_info_user_id", columnList = "USER_ID"),
})
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "USER_ID", updatable = false)
    private Long userId;

    @Column(name = "POINT")
    private Long point = 0L;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    public UserInfo() {}

    @Builder
    public UserInfo(Long userId) {
        this.userId = userId;
    }

    public UserInfo(Long userId, Long point) {
        this.userId = userId;
        this.point = point;
    }

    public UserInfo(Long id, Long userId, Long point, LocalDateTime currentTime) {
        this.id = id;
        this.userId = userId;
        this.point = point;
        this.updatedAt = currentTime;
    }
}
