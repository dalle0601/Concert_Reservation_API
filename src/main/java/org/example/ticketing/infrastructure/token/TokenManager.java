package org.example.ticketing.infrastructure.token;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TokenManager {

    private final RedissonClient redissonClient;

    public Map<String, String> issueToken(String userId) {
        String tokenKey = "token:" + userId;
        RBucket<String> tokenBucket = redissonClient.getBucket(tokenKey);
        String token = UUID.randomUUID().toString();
        tokenBucket.set(token, 3, TimeUnit.MINUTES); // 토큰을 1분간 유효하도록 저장
        Map<String, String> tokenInfo = new HashMap<>();
        tokenInfo.put("token", token);
        long expirationTimeMillis = tokenBucket.remainTimeToLive() / 1000;
        tokenInfo.put("expirationTime", String.valueOf(expirationTimeMillis));
        return tokenInfo;
    }

    public Map<String, String> getCheckTokenInfo(String userId) {
        String tokenKey = "token:" + userId;
        RBucket<String> tokenBucket = redissonClient.getBucket(tokenKey);
        Map<String, String> tokenInfo = new HashMap<>();
        if (tokenBucket.isExists()) { // 토큰이 존재하면
            String token = tokenBucket.get(); // 토큰 값 가져오기
            long expirationTimeMillis = tokenBucket.remainTimeToLive() / 1000;
            tokenInfo.put("token", token);
            tokenInfo.put("expirationTime", String.valueOf(expirationTimeMillis));
        } else {
            tokenInfo.put("token", null);
        }

        return tokenInfo;
    }


    public Long getValidTokenCount() {
        RKeys keys = redissonClient.getKeys();
        Iterable<String> allTokens = keys.getKeysByPattern("token:*");
        long count = 0;
        for (String token : allTokens) {
            count++;
        }
        return count;
    }

    public void deleteToken(String userId) {
        String tokenKey = "token:" + userId;
        RBucket<String> tokenBucket = redissonClient.getBucket(tokenKey);
        if (tokenBucket.isExists()) {
            tokenBucket.delete();
        }
    }
}