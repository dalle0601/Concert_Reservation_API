package org.example.ticketing.api.usecase.point;

import org.example.ticketing.api.dto.request.PointHistorySaveRequestDTO;
import org.example.ticketing.api.dto.request.PointRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.PointHistorySaveResponseDTO;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChargePointUseCase {
    private final UserService userService;
    private final WritePointHistoryUseCase writePointHistoryUseCase;

    public ChargePointUseCase(UserService userService, WritePointHistoryUseCase writePointHistoryUseCase) {
        this.userService = userService;
        this.writePointHistoryUseCase = writePointHistoryUseCase;
    }

    public PointHistorySaveResponseDTO execute(PointRequestDTO pointRequestDTO){
        if(pointRequestDTO.point() <= 0) {
            return new PointHistorySaveResponseDTO("0 또는 마이너스 포인트는 충전 불가능합니다.", null);
        }

        try {
            UserInfo userInfo = userService.findUserInfo(new UserRequestDTO(pointRequestDTO.userId()));
            if (userInfo != null) {
                UserInfo updateUserPoint = new UserInfo(userInfo.getId(), userInfo.getUserId(), userInfo.getPoint() + pointRequestDTO.point(), LocalDateTime.now());
                UserInfo afterChargePointUserInfo = userService.chargePoint(updateUserPoint);

                PointHistorySaveResponseDTO pointHistorySaveResponseDTO = writePointHistoryUseCase.execute(new PointHistorySaveRequestDTO(afterChargePointUserInfo.getUserId(), afterChargePointUserInfo.getPoint(), "charge"));
                if(pointHistorySaveResponseDTO.pointHistory() == null){
                    return new PointHistorySaveResponseDTO(pointHistorySaveResponseDTO.message(), null);
                }

                return new PointHistorySaveResponseDTO("포인트 충전 성공", pointHistorySaveResponseDTO.pointHistory());
            } else {
                // 유저가 존재하지 않을경우
                return new PointHistorySaveResponseDTO("해당 유저가 존재하지 않습니다.", null);
            }
        } catch (Exception e) {
            return new PointHistorySaveResponseDTO("유저 조회 중 오류가 발생했습니다.", null);
        }
    }
}
