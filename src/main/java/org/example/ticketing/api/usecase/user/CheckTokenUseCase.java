package org.example.ticketing.api.usecase.user;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.infrastructure.token.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckTokenUseCase {
    private final TokenManager tokenManager;

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        long userId = userRequestDTO.userId();
        Map<String, String> token = tokenManager.getCheckTokenInfo(userId);
        if(token.get("token") != null) {
            return new TokenResponseDTO("유효한 토큰입니다.", token.get("token"), token.get("expirationTime"));
        } else {
            return new TokenResponseDTO("유효하지 않은 토큰입니다.", null, null);
        }
    }
}
