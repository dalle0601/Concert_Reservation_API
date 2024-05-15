package org.example.ticketing.domain.point.repository;

import org.example.ticketing.domain.point.model.PointHistory;

public interface PointHistoryRepository {
    PointHistory save(PointHistory pointHistory);
}
