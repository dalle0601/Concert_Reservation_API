package org.example.ticketing.api.dto.response;

import java.time.LocalDateTime;

public record SeatResponseDTO (
        Long seat_id,
        String seat_number,
        Long cost,
        String seat_status
){
}
