package org.example.ticketing.api.dto.concert.response;

import java.util.List;

public record ConcertResponseDTO (
        String message,
        List<ConcertWithSeatCountDTO> list
){
}
