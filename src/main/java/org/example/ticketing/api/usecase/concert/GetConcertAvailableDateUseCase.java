package org.example.ticketing.api.usecase.concert;

import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.concert.response.ConcertResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetConcertAvailableDateUseCase {

    private final ConcertService concertService;
    private final CheckTokenUseCase checkTokenUseCase;
    public GetConcertAvailableDateUseCase(ConcertService concertService, CheckTokenUseCase checkTokenUseCase) {
        this.concertService = concertService;
        this.checkTokenUseCase = checkTokenUseCase;
    }

    public ConcertResponseDTO execute(UserRequestDTO userRequestDTO) throws Exception {
        try {
            TokenResponseDTO tokenResponseDTO = checkTokenUseCase.execute(userRequestDTO);
            String isValidToken = tokenResponseDTO.token();

            if (isValidToken != null) {
                // 토큰이 유효한 경우, 콘서트 정보 조회
                List<Concert> concertList = concertService.getConcertDate(LocalDateTime.now());
                return new ConcertResponseDTO("이용가능한 콘서트 날짜 조회 성공", concertList);
            } else {
                // 토큰이 유효하지 않은 경우
                return new ConcertResponseDTO("토큰이 유효하지 않습니다.", null);
            }
        } catch (Exception e) {
            return new ConcertResponseDTO("콘서트 조회 중 오류가 발생했습니다.", null);
        }
    }
}
