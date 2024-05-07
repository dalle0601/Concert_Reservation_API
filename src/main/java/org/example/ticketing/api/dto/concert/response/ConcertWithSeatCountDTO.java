package org.example.ticketing.api.dto.concert.response;

import org.example.ticketing.domain.concert.model.Concert;

public record ConcertWithSeatCountDTO (
        Concert concert,
        Long availableSeatCount
){
}
