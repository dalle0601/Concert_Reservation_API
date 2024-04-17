package org.example.ticketing.api.dto.response;

import java.util.List;

public record SeatResponseDTO (
        String message,
        List<SeatDTO> seatList
){
}
