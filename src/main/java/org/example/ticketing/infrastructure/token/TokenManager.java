package org.example.ticketing.infrastructure.token;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TokenManager {

    private final RedissonClient redissonClient;

    public Map<String, String> issueToken(Long userId) {
        String tokenKey = "token:" + userId;
        RBucket<String> tokenBucket = redissonClient.getBucket(tokenKey);
        String token = UUID.randomUUID().toString();
        tokenBucket.set(token, 1, TimeUnit.MINUTES); // 토큰을 5분간 유효하도록 저장

        Map<String, String> tokenInfo = new HashMap<>();
        tokenInfo.put("token", token);
        long expirationTimeMillis = System.currentTimeMillis() + tokenBucket.remainTimeToLive();
        tokenInfo.put("expirationTime", Instant.ofEpochMilli(expirationTimeMillis).toString());

        return tokenInfo;
    }

    public Long getValidTokenCount() {
        RKeys keys = redissonClient.getKeys();
        Iterable<String> allTokens = keys.getKeysByPattern("token:*");
        long count = 0;
        for (String token : allTokens) {
            // 여기에서 추가적인 유효성 검사를 할 수 있습니다. 예를 들어, 특정 조건에 맞는 토큰만 카운트.
            count++;
        }
        return count;
    }
}