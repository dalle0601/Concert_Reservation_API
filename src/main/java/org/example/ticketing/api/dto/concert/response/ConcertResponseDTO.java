package org.example.ticketing.api.dto.concert.response;

import org.example.ticketing.domain.concert.model.Concert;

import java.util.List;

public record ConcertResponseDTO (
        String message,
        List<Concert> concertList
){
}