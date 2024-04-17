package org.example.ticketing.domain.concert.service;

import org.example.ticketing.api.dto.request.SeatReqeustDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableDateUseCase;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableSeatUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConcertService {
    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }
    public List<Concert> getConcertDate(LocalDateTime currentDate) {
        return concertRepository.getConcertDateByToday(currentDate);
    }
    public List<Concert> findByConcertId(Long concertId) {
        return concertRepository.findByConcertId(concertId);
    }
}
