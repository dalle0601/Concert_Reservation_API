package org.example.ticketing.domain.point.repository;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.point.model.PointHistory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository{
    private final PointJpaRepository pointJpaRepository;

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return pointJpaRepository.save(pointHistory);
    }
}
