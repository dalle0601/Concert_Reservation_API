package org.example.ticketing.api.usecase.concert;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.concert.response.SeatDTO;
import org.example.ticketing.api.dto.concert.response.SeatResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.UpdateTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class GetConcertAvailableSeatUseCase {
    private final UpdateTokenUseCase checkTokenUseCase;
    private final ReservationService reservationService;
    private final ConcertService concertService;

    public SeatResponseDTO execute(UserRequestDTO userRequestDTO, Long concertId) throws Exception {
        try {
            TokenResponseDTO tokenResponseDTO = checkTokenUseCase.execute(userRequestDTO);
            String isValidToken = tokenResponseDTO.token();

            if (isValidToken != null) {
                // 토큰이 유효한 경우, 콘서트 정보 조회
                Concert concert = concertService.findByConcertId(concertId);
                if(concert == null) {
                    return new SeatResponseDTO("해당 콘서트는 존재하지 않습니다.", null);
                }

                List<SeatDTO> allSeat = new ArrayList<>();
                for (int i = 1; i <= 50; i++){
                    if(i <= 25) {
                        allSeat.add(new SeatDTO((long)i, "A"+i, 50000L, "available"));
                    } else {
                        allSeat.add(new SeatDTO((long)i, "B"+(i-25), 45000L, "available"));
                    }
                }
                String[] statuses = {"reserved", "temporary"};
                List<Reservation> seatList = reservationService.findReservedOrTempSeat(statuses, concertId);

                List<SeatDTO> availableSeats = allSeat.stream()
                        .filter(seat -> seatList.stream().noneMatch(reservation -> Objects.equals(reservation.getSeatId(), seat.seat_id())))
                        .toList();

                return new SeatResponseDTO("이용가능한 콘서트 좌석 조회 성공", availableSeats);
            } else {
                // 토큰이 유효하지 않은 경우
                return new SeatResponseDTO("토큰이 유효하지 않습니다.", null);
            }
        } catch (Exception e) {
            return new SeatResponseDTO("좌석 조회 중 오류가 발생했습니다.", null);
        }
    }
}
