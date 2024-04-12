package org.example.ticketing.domain.reservation.repository;

import org.example.ticketing.domain.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

}
