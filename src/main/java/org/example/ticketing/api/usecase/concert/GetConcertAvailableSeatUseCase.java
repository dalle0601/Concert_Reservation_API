package org.example.ticketing.api.usecase.concert;

import org.example.ticketing.api.dto.request.SeatReqeustDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetConcertAvailableSeatUseCase {
    private ConcertRepository concertRepository;

    public GetConcertAvailableSeatUseCase(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<Seat> execute(UserRequestDTO userRequestDTO, SeatReqeustDTO seatReqeustDTO) {
//        TokenResponseDTO tokenResponseDTO = confirmQueueUseCase.execute(userRequestDTO);
//        String checkToken = tokenResponseDTO.token().split("/")[1];
//
//        if (checkToken.equals("onGoing")) {
//            // concert Table 조회
//            return concertRepository.getConcertSeatById(seatReqeustDTO.concert_id());
//        } else {
//            String eMessage = "";
//            if(checkToken.equals("onWait")) {
//                eMessage = "현재 " + tokenResponseDTO.token().split("/")[2] + "명 대기상태 입니다.";
//            } else {
//                eMessage = "토큰이 만료되었습니다.";
//            }
//            throw new IllegalStateException(eMessage);
//        }
        return null;
    }
}
