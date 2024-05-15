package org.example.ticketing.domain.point.service;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.point.model.PointHistory;
import org.example.ticketing.domain.point.repository.PointHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;

    public PointHistory save(PointHistory pointHistory){
        return pointHistoryRepository.save(pointHistory);
    }
}
