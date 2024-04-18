package org.example.ticketing.api.usecase.point;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ConcertResponseDTO;
import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetPointUseCase {
    private final UserService userService;

    public GetPointUseCase(UserService userService) {
        this.userService = userService;
    }
    public PointResponseDTO execute(Long userId){
        try {
            UserInfo userInfo = userService.findUserInfo(new UserRequestDTO(userId));
            if (userInfo != null) {
                return new PointResponseDTO("유저 정보 조회 성공", userInfo.getUserId(), userInfo.getPoint());
            } else {
                // 유저가 존재하지 않을경우
                return new PointResponseDTO("해당 유저가 존재하지 않습니다.", null, null);
            }
        } catch (Exception e) {
            return new PointResponseDTO("유저 조회 중 오류가 발생했습니다.", null, null);
        }
    }
}
