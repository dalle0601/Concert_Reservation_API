package org.example.ticketing.domain.user.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="QUEUE")
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long queueId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public Queue() {}
    @Builder
    public Queue(Long user_id, String status, LocalDateTime updated_at, LocalDateTime created_at) {
        this.userId = user_id;
        this.status = status;
        this.updatedAt = updated_at;
        this.createdAt = created_at;
    }
    public Queue(Long queue_id, Long user_id, String status, LocalDateTime updated_at, LocalDateTime created_at) {
        this.queueId = queue_id;
        this.userId = user_id;
        this.status = status;
        this.updatedAt = updated_at;
        this.createdAt = created_at;
    }
}

