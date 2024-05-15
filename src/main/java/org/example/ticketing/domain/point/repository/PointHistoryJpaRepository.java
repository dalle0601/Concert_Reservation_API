package org.example.ticketing.domain.point.repository;

import org.example.ticketing.domain.point.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistory, Long> {
}
