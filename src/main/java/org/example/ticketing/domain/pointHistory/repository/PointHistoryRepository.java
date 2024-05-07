package org.example.ticketing.domain.pointHistory.repository;

import org.example.ticketing.domain.pointHistory.model.PointHistory;

public interface PointHistoryRepository {
    PointHistory save(PointHistory pointHistory);
}
