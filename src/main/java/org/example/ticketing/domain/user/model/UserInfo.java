package org.example.ticketing.domain.user.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Table(name="USER_INFO")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "USER_ID", updatable = false)
    private Long userId;

    @Column(name = "POINT")
    private Long point;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    public UserInfo() {}

    @Builder
    public UserInfo(Long user_id) {
        this.userId = user_id;
    }


}
