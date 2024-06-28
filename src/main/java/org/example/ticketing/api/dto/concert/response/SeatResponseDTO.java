package org.example.ticketing.api.dto.concert.response;

import java.util.List;

public record SeatResponseDTO (
        String message,
        List<SeatDTO> list
){
}
