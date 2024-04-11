package org.example.ticketing.api.usecase.concert;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.ConfirmUserTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetConcertAvailableDateUseCase {

    private ConfirmUserTokenUseCase confirmUserTokenUseCase;
    private ConcertRepository concertRepository;

    public GetConcertAvailableDateUseCase(ConfirmUserTokenUseCase confirmUserTokenUseCase, ConcertRepository concertRepository) {
        this.confirmUserTokenUseCase = confirmUserTokenUseCase;
        this.concertRepository = concertRepository;
    }

    public List<Concert> execute(UserRequestDTO userRequestDTO) {
        TokenResponseDTO tokenResponseDTO = confirmUserTokenUseCase.execute(userRequestDTO);
        String checkToken = tokenResponseDTO.token().split("/")[1];

        if (checkToken.equals("onGoing")) {
            // concert Table 조회
            return concertRepository.getConcertByDate(LocalDateTime.now());
        } else {
            String eMessage = "";
            if(checkToken.equals("onWait")) {
                eMessage = "현재 " + tokenResponseDTO.token().split("/")[2] + "명 대기상태 입니다.";
            } else {
                eMessage = "토큰이 만료되었습니다.";
            }
            throw new IllegalStateException(eMessage);
        }
    }
}
