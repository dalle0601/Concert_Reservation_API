package org.example.ticketing.api.dto.response;

import java.time.LocalDateTime;

public record ConcertResponseDTO (
        Long concert_id,
        String concert_title,
        LocalDateTime concert_date,
        Long maxSeatCnt,
        Long availableSeatCnt
){
}
