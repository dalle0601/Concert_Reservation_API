package org.example.ticketing.infrastructure.lock;

import java.util.concurrent.TimeUnit;

public interface DistributedLockInterface {
    boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;
    void unlock(String lockKey);
}
