package org.example.ticketing.api.usecase.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.reservation.response.ReservationListResponseDTO;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.UserReservationResponseDTO;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReservationUseCase {

    private final ReservationService reservationService;

    @Transactional
    public ReservationListResponseDTO execute(UserRequestDTO userRequestDTO) {
        try {
            List<UserReservationResponseDTO> reservations = reservationService.findByUserIdAndStatusNotLike(userRequestDTO.userId(), "available")
                    .stream()
                    .map(r -> new UserReservationResponseDTO(
                            (Long) r[0],
                            (Long) r[1],
                            (Long) r[2],
                            (Long) r[3],
                            getSeatName((Long) r[3]),
                            (String) r[4],
                            (Long) r[5],
                            (LocalDateTime) r[6],
                            (LocalDateTime) r[7],
                            (String) r[8],
                            (String) r[9]
                    ))
                    .collect(Collectors.toList());
//            List<UserReservationResponseDTO> reservations = reservationService.findByUserIdAndStatusNotLike(userRequestDTO.userId(), "available");
            return new ReservationListResponseDTO("예약정보", reservations);
        } catch (Exception e) {
            return new ReservationListResponseDTO("예약정보를 가져오는데 오류가 발생했습니다.", null);
        }
    }
    private String getSeatName(Long seatId) {
        int row = (int) ((seatId - 1) / 25) + 1;
        int column = (int) ((seatId - 1) % 25) + 1;
        return (char) ('A' + row - 1) + String.valueOf(column);
    }
}