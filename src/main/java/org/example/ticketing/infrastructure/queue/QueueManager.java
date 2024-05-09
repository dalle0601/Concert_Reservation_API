package org.example.ticketing.infrastructure.queue;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RList;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueManager {

    private final RedissonClient redissonClient;

    public void addToQueue(Long userId) {
        RQueue<String> queue = redissonClient.getQueue("waitingQueue");
        queue.offer(userId.toString());
    }

    public String getNextInQueue() {
        RQueue<String> queue = redissonClient.getQueue("waitingQueue");
        return queue.poll(); // 대기열에서 다음 사용자를 반환하고 대기열에서 제거
    }

    public int getQueuePosition(Long userId) {
        RList<String> queue = redissonClient.getList("waitingQueue");
        // 대기열에서 사용자의 인덱스를 찾습니다.
        int position = queue.indexOf(userId.toString());
        // Redis 리스트는 0부터 인덱싱하므로, 사용자가 대기열에 있을 경우 실제 위치는 인덱스 + 1입니다.
        return position >= 0 ? position + 1 : -1;
    }
}