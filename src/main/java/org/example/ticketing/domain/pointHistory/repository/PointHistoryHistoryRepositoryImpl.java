package org.example.ticketing.domain.pointHistory.repository;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.pointHistory.model.PointHistory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointHistoryHistoryRepositoryImpl implements PointHistoryRepository {
    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return pointHistoryJpaRepository.save(pointHistory);
    }
}
