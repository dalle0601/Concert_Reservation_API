package org.example.ticketing.api.usecase.reservation;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.infrastructure.lock.DistributedLock;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MakeReservationUseCase {

    private final DistributedLock distributedLock;
    private final ReserveUseCase reserveUseCase;

    public ReservationResponseDTO execute(ReservationRequestDTO reservationRequestDTO) throws InterruptedException {
        String lockKey = reservationRequestDTO.concertId().toString() + reservationRequestDTO.seatId().toString();

        boolean acquireLock = distributedLock.tryLock(lockKey, 5, 1, TimeUnit.SECONDS);
        if (!acquireLock) {
            return null;
        }

        try {
            return reserveUseCase.reserve(reservationRequestDTO);
        } catch (DataAccessException e) {
            return new ReservationResponseDTO("예약 중 데이터베이스 오류가 발생했습니다.", null);
        } catch (Exception e) {
            return new ReservationResponseDTO("예약 중 오류가 발생했습니다.", null);
        } finally {
            distributedLock.unlock(lockKey); // 락 해제
        }
    }


}
