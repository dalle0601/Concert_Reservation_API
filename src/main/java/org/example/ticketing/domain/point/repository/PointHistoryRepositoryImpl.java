package org.example.ticketing.domain.point.repository;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.point.model.PointHistory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return pointHistoryJpaRepository.save(pointHistory);
    }
}
