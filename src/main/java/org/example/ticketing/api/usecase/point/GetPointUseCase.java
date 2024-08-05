package org.example.ticketing.api.usecase.point;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.point.response.PointResponseDTO;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPointUseCase {
    private final UserService userService;

    public PointResponseDTO execute(String userId){
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
