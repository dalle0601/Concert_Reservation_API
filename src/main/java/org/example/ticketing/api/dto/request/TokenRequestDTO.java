package org.example.ticketing.api.dto.request;

import java.time.LocalDateTime;

public record TokenRequestDTO(
        Long userId,
        String token,
        LocalDateTime expiredTime
) {
}
