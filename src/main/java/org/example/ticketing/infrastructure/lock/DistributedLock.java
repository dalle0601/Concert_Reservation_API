package org.example.ticketing.infrastructure.lock;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.api.RLock;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class DistributedLock implements DistributedLockInterface {
    private final RedissonClient redissonClient;

    @Override
    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.tryLock(waitTime, leaseTime, timeUnit);
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }
}
