package org.example.ticketing.domain.user.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

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
    private String userId;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public Queue() {}
    @Builder
    public Queue(String userId, LocalDateTime updated_at) {
        this.userId = userId;
        this.updatedAt = updated_at;
    }
    public Queue(Long queue_id, String userId, LocalDateTime updated_at) {
        this.queueId = queue_id;
        this.userId = userId;
        this.updatedAt = updated_at;
    }
}

