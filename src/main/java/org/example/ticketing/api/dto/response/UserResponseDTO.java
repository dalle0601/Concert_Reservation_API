package org.example.ticketing.api.dto.response;

import java.time.LocalDateTime;

public record UserResponseDTO (
        Long user_id,
        Long point
) {
}
