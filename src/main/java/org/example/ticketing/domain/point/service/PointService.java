package org.example.ticketing.domain.point.service;

import org.example.ticketing.domain.point.model.PointHistory;
import org.example.ticketing.domain.point.repository.PointRepository;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public PointHistory save(PointHistory pointHistory){
        return pointRepository.save(pointHistory);
    }
}
