package org.example.ticketing.api.usecase.point;

import org.example.ticketing.api.dto.request.PointHistorySaveRequestDTO;
import org.example.ticketing.api.dto.response.PointHistorySaveResponseDTO;
import org.example.ticketing.domain.point.model.PointHistory;
import org.example.ticketing.domain.point.service.PointService;
import org.springframework.stereotype.Service;
@Service
public class WritePointHistoryUseCase {
    private final PointService pointService;

    public WritePointHistoryUseCase(PointService pointService) {
        this.pointService = pointService;
    }

    public PointHistorySaveResponseDTO execute(PointHistorySaveRequestDTO pointHistorySaveRequestDTO){
        try {
            PointHistory savePointHistory = pointService.save(new PointHistory(pointHistorySaveRequestDTO.userId(), pointHistorySaveRequestDTO.point(), pointHistorySaveRequestDTO.status()));
            if (savePointHistory != null) {
                return new PointHistorySaveResponseDTO("포인트 내역 저장 성공", savePointHistory);
            } else {
                // 포인트 내역이 반환되지 않을 경우
                return new PointHistorySaveResponseDTO("포인트 내역 저장 실패", null);
            }
        } catch (Exception e) {
            return new PointHistorySaveResponseDTO("포인트 내역 저장 중 오류가 발생했습니다.", null);
        }
    }
}
