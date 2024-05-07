package org.example.ticketing.domain.pointHistory.repository;

import org.example.ticketing.domain.pointHistory.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistory, Long> {
}
