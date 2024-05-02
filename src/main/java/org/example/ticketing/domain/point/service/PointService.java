package org.example.ticketing.domain.point.service;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.point.model.PointHistory;
import org.example.ticketing.domain.point.repository.PointRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public PointHistory save(PointHistory pointHistory){
        return pointRepository.save(pointHistory);
    }
}
