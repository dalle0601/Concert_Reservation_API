package org.example.ticketing.api.usecase.reservations;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableDateUseCase;
import org.example.ticketing.api.usecase.user.ConfirmUserTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetConcertAvailableDateUseCaseTest {
    private GetConcertAvailableDateUseCase getConcertAvailableDateUseCase;

    @Mock
    private ConcertRepository concertRepository;
    @Mock
    private ConfirmUserTokenUseCase confirmUserTokenUseCase;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getConcertAvailableDateUseCase = new GetConcertAvailableDateUseCase(confirmUserTokenUseCase, concertRepository);

    }
    @Test
    @DisplayName("예약가능한 날짜를 호출했지만 대기상태가 onWait인 경우 ->> 예외발생")
    void getAvailableDateTestOnWait() {
        Long userId = 1L;
        when(confirmUserTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("abcd-efgh-jklm/onWait/5"));
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            getConcertAvailableDateUseCase.execute(new UserRequestDTO(userId));
        });
        assertEquals("현재 5명 대기상태 입니다.", exception.getMessage());

    }
    @Test
    @DisplayName("예약가능한 날짜를 호출했고 대기상태가 onGoing 경우")
    void getAvailableDateTestOnGoing() {
        LocalDateTime nowDate = LocalDateTime.now();
        when(confirmUserTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("abcd-efgh-jklm/onGoing"));
        List<Concert> concerts = new ArrayList<>();

        concerts.add(new Concert(1L, "1번 콘서트", LocalDateTime.of(2024, 5, 11, 17, 30), 50L, 11L, LocalDateTime.now()));
        concerts.add(new Concert(4L, "4번 콘서트", LocalDateTime.of(2024, 5, 17, 18, 0), 50L, 3L, LocalDateTime.now()));
        concerts.add(new Concert(6L, "6번 콘서트", LocalDateTime.of(2024, 6, 4, 16, 30), 50L, 15L, LocalDateTime.now()));
        concerts.add(new Concert(11L, "11번 콘서트", LocalDateTime.of(2024, 8, 22, 18, 30), 50L, 25L, LocalDateTime.now()));
        when(concertRepository.getConcertByDate(any())).thenReturn(concerts);

        List<Concert> result = getConcertAvailableDateUseCase.execute(new UserRequestDTO(1L));
        assertNotNull(result);
        assertEquals(4, result.size());
    }
}
