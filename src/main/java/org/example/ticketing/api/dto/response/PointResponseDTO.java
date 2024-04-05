package org.example.ticketing.api.dto.response;

import java.time.LocalDateTime;

public record PointResponseDTO (
        Long user_id,
        Long point,
        LocalDateTime execute_time
){
    public PointResponseDTO(Long user_id, Long point){
        this(user_id, point, LocalDateTime.now());
    }
}
