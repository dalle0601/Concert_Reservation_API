package org.example.ticketing.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.user.model.Queue;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository{
    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Optional<Queue> findByUserId(Long userId) {
        return queueJpaRepository.findByUserId(userId);
    }

    @Override
    public Queue enterQueue(Long userId) {
        Queue queue = new Queue(userId, LocalDateTime.now());
        return queueJpaRepository.save(queue);
    }

//    @Override
//    public Queue queueInsertOrUpdate(Long userId, String status) {
//        Optional<Queue> existingQueueOptional = queueJpaRepository.findByUserId(userId);
//
//        if (existingQueueOptional.isPresent()) {
//            Queue existingQueue = existingQueueOptional.get();
//            existingQueue = new Queue(existingQueue.getQueueId(), userId, LocalDateTime.now());
//            return queueJpaRepository.save(existingQueue);
//        } else {
//            LocalDateTime nowDate = LocalDateTime.now();
//            Queue newQueue = new Queue(userId, nowDate);
//            return queueJpaRepository.save(newQueue); // 새로운 엔티티 저장
//        }
//    }

    @Override
    public Long findQueueCount(LocalDateTime myTime) {
        return queueJpaRepository.findQueueCount(myTime);
    }

    @Override
    public void deleteQueue(Long userId) throws Exception {
        Optional<Queue> deleteValue = queueJpaRepository.findByUserId(userId);
        if(deleteValue.isPresent()) {
            queueJpaRepository.delete(deleteValue.get());
        } else {
            throw new Exception("대기열이 존재하지 않습니다.");
        }
    }

    @Override
    public List<Queue> findFirstNOrderByUpdatedAtAsc(int count) {
        return queueJpaRepository.findFirstNOrderByUpdatedAtAsc(count);
    }

}
