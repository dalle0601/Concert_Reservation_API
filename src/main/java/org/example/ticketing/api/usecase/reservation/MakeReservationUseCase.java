package org.example.ticketing.api.usecase.reservation;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.example.ticketing.infrastructure.lock.DistributedLock;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MakeReservationUseCase {

    private final DistributedLock distributedLock;
    private final ReserveUseCase reserveUseCase;
    private final ReservationService reservationService;

    public ReservationResponseDTO execute(ReservationRequestDTO reservationRequestDTO) throws InterruptedException {
        String lockKey = reservationRequestDTO.concertId().toString() + reservationRequestDTO.seatId().toString();

        boolean acquireLock = distributedLock.tryLock(lockKey, 5, 1, TimeUnit.SECONDS);
        if (!acquireLock) {
            throw new RuntimeException("LOCK_FAILED");
        }

        try {
            return reserveUseCase.reserve(reservationRequestDTO);
        } catch (DataAccessException e) {
            throw new RuntimeException("DATABASE_ERROR");
        } catch (Exception e) {
            throw new RuntimeException("GENERAL_ERROR");
        } finally {
            distributedLock.unlock(lockKey); // 락 해제
        }
    }
}
