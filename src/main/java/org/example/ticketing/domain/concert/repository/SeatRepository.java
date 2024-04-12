package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.domain.concert.model.Seat;

public interface SeatRepository {
    Seat updateSeatStatus(Long seat_id, String seat_status);
}
