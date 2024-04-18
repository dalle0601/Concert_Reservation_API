package org.example.ticketing.domain.point.repository;

import org.example.ticketing.domain.point.model.PointHistory;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepositoryImpl implements PointRepository{
    private final PointJpaRepository pointJpaRepository;

    public PointRepositoryImpl(PointJpaRepository pointJpaRepository) {
        this.pointJpaRepository = pointJpaRepository;
    }

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return pointJpaRepository.save(pointHistory);
    }
}
