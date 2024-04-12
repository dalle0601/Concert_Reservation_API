package org.example.ticketing.domain.point.repository;

import org.example.ticketing.domain.point.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJpaRepository extends JpaRepository<PointHistory, Long> {
}
