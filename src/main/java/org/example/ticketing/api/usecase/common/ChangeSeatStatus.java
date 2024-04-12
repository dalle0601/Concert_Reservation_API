package org.example.ticketing.api.usecase.common;

import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.example.ticketing.domain.concert.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangeSeatStatus {
    private SeatRepository seatRepository;

    public ChangeSeatStatus(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }

    public Seat execute(Long seat_id, String status) {
        return seatRepository.updateSeatStatus(seat_id, status);
    }
}
