package org.example.ticketing.api.usecase.concert;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ConcertResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetConcertAvailableDateUseCaseTest {
    private GetConcertAvailableDateUseCase getConcertAvailableDateUseCase;

    @Mock
    private ConcertService concertService;

    @Mock
    private CheckTokenUseCase checkTokenUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getConcertAvailableDateUseCase = new GetConcertAvailableDateUseCase(concertService, checkTokenUseCase);

    }
    @Test
    @DisplayName("예약가능한 날짜를 호출했지만 대기상태가 onWait인 경우 ->> 예외발생")
    void getAvailableDateTestOnWait() throws Exception {
        Long userId = 1L;
        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("토큰이 확인되지 않습니다.", null, null));

        ConcertResponseDTO actureValue = getConcertAvailableDateUseCase.execute(new UserRequestDTO(userId));

        assertEquals("토큰이 유효하지 않습니다.", actureValue.message());
        assertEquals(null, actureValue.concertList());
    }

    @Test
    @DisplayName("예약가능한 날짜를 호출했고 대기상태가 onGoing 경우")
    void getAvailableDateTestOnGoing() throws Exception {
        Long userId = 1L;
        List<Concert> concerts = new ArrayList<>();
        concerts.add(new Concert(1L, "첫번째 콘서트", LocalDateTime.of(2024, 5, 11, 17, 30), LocalDateTime.now()));
        concerts.add(new Concert(2L, "22번째 콘서트", LocalDateTime.of(2024, 6, 6, 18, 30), LocalDateTime.now()));
        concerts.add(new Concert(3L, "333번째 콘서트", LocalDateTime.of(2024, 7, 17, 17, 00), LocalDateTime.now()));

        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("유효한 토큰입니다.", "abcd-efgh-ijkl", LocalDateTime.now().plusMinutes(10)));
        when(concertService.getConcertDate(any())).thenReturn(concerts);

        ConcertResponseDTO actureValue = getConcertAvailableDateUseCase.execute(new UserRequestDTO(userId));

        assertEquals("이용가능한 콘서트 날짜 조회 성공", actureValue.message());
        assertEquals(concerts, actureValue.concertList());
    }
}
