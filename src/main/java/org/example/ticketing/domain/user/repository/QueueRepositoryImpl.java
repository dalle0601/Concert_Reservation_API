package org.example.ticketing.domain.user.repository;

import org.example.ticketing.api.dto.response.QueueWaitInfoResponseDTO;
import org.example.ticketing.domain.user.model.Queue;
import org.example.ticketing.domain.user.model.projection.QueueWaitInfo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class QueueRepositoryImpl implements QueueRepository{
    private final QueueJpaRepository queueJpaRepository;
    public QueueRepositoryImpl(QueueJpaRepository queueJpaRepository) {
        this.queueJpaRepository = queueJpaRepository;
    }

    @Override
    public Optional<Queue> findByUserId(Long user_id) {
        return queueJpaRepository.findByUserId(user_id);
    }

    @Override
    public Queue enterQueue(Long userId) {
        Queue queue = new Queue(userId, LocalDateTime.now());
        return queueJpaRepository.save(queue);
    }

//    @Override
//    public Queue queueInsertOrUpdate(Long user_id, String status) {
//        Optional<Queue> existingQueueOptional = queueJpaRepository.findByUserId(user_id);
//
//        if (existingQueueOptional.isPresent()) {
//            Queue existingQueue = existingQueueOptional.get();
//            existingQueue = new Queue(existingQueue.getQueueId(), user_id, LocalDateTime.now());
//            return queueJpaRepository.save(existingQueue);
//        } else {
//            LocalDateTime nowDate = LocalDateTime.now();
//            Queue newQueue = new Queue(user_id, nowDate);
//            return queueJpaRepository.save(newQueue); // 새로운 엔티티 저장
//        }
//    }

    @Override
    public Long findQueueCount() {
        return queueJpaRepository.findQueueCount();
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

}
