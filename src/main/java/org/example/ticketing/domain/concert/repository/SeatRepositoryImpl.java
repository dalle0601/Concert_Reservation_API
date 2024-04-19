package org.example.ticketing.domain.concert.repository;

import org.example.ticketing.domain.concert.model.Seat;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SeatRepositoryImpl implements SeatRepository{
    private final SeatJpaRepository seatJpaRepository;

    public SeatRepositoryImpl(SeatJpaRepository seatJpaRepository) {
        this.seatJpaRepository = seatJpaRepository;
    }
    @Override
    public Seat updateSeatStatus(Long seat_id, String seat_status) {
        Optional<Seat> existingSeatOptional = seatJpaRepository.findById(seat_id);

        if (existingSeatOptional.isPresent()) {
            Seat existingSeat = existingSeatOptional.get();
            existingSeat = new Seat(existingSeat.getSeatId(), existingSeat.getConcertId(), existingSeat.getSeatName(), existingSeat.getSeatCost(), seat_status);
            return seatJpaRepository.save(existingSeat);
        } else {
            throw new IllegalArgumentException("해당 좌석이 존재하지 않습니다.");
        }
    }
}
