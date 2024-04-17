package org.example.ticketing.api.dto.response;

public record SeatDTO (
        Long seat_id,
        String seat_number,
        Long cost,
        String seat_status
){

}
