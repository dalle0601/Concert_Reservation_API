package org.example.ticketing.api.dto.concert.response;

public record SeatDTO (
        Long seat_id,
        String seat_number,
        Long cost,
        String seat_status
){

}
