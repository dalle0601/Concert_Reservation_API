package org.example.ticketing.api.dto.user.response;

import org.example.ticketing.domain.user.model.UserInfo;

public record UserResponseDTO (
        String message,
        UserInfo userInfo
) {
}
