package org.example.ticketing.domain.user.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Table(name="USER_INFO")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "POINT")
    private Long point;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    private Date createdAt;

    @Builder
    public UserInfo(String uuid, String userId) {
        this.uuid = uuid;
        this.userId = userId;
    }
}
