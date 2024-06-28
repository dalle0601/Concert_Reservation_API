package org.example.ticketing.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class RedisKeyExpirationListener implements MessageListener {

    private final ReservationService reservationService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);

        if (key.startsWith("reservation:")) {
            String[] parts = key.split(":");
            Long concertId = Long.parseLong(parts[1]);
            Long seatId = Long.parseLong(parts[2]);

            reservationService.updateReservationStatusToAvailable(concertId, seatId);
        }
    }
}
