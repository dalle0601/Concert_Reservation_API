package org.example.ticketing.domain.point.repository;

import org.example.ticketing.domain.point.model.PointHistory;

public interface PointRepository {
    PointHistory save(PointHistory pointHistory);
}
