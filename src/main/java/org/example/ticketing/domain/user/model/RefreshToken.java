package org.example.ticketing.domain.user.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 14440)
public class RefreshToken {
    @Id
    private final String refreshToken;
    private final String userId;

    public RefreshToken(String refreshToken, String userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}
