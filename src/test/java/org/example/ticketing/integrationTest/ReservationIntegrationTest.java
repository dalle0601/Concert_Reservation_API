package org.example.ticketing.integrationTest;

import jakarta.transaction.Transactional;
import org.example.ticketing.api.dto.concert.response.ConcertResponseDTO;
import org.example.ticketing.api.dto.concert.response.SeatResponseDTO;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableDateUseCase;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableSeatUseCase;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.api.usecase.user.EnterQueueUseCase;
import org.example.ticketing.infrastructure.lock.DistributedLockInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class ReservationIntegrationTest {

    @Autowired
    private EnterQueueUseCase enterQueueUseCase;

    @Autowired
    private CheckTokenUseCase checkTokenUseCase;

    @Autowired
    private GetConcertAvailableDateUseCase getConcertAvailableDateUseCase;

    @Autowired
    private GetConcertAvailableSeatUseCase getConcertAvailableSeatUseCase;

    @Autowired
    private MakeReservationUseCase makeReservationUseCase;

    @Autowired
    private DistributedLockInterface distributedLockInterface;

    private Long userId;

    @BeforeEach
    public void setUp() {
        userId = 1L;
    }

    @Test
    @DisplayName("대기열 등록, 유효토큰 발급, 확인 및 콘서트, 좌석 조회 후 예약성공 확인")
    public void reservation_Success() throws Exception {
        // 사용자가 대기열에 들어가고 토큰을 받아온다.
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        QueueResponseDTO queueResponseDTO = enterQueueUseCase.execute(userRequestDTO);
        assertNotNull(queueResponseDTO.token());

        // 토큰 확인한다.
        TokenResponseDTO tokenResponseDTO = checkTokenUseCase.execute(userRequestDTO);
        assertNotNull(tokenResponseDTO.token());

        // 콘서트 예약가능 날짜를 조회한다.
        ConcertResponseDTO concertResponseDTO = getConcertAvailableDateUseCase.execute(userRequestDTO);
        assertNotNull(concertResponseDTO);

        // 1번 콘서트의 예약가능 좌석을 조회한다.
        SeatResponseDTO seatResponseDTO = getConcertAvailableSeatUseCase.execute(userRequestDTO, concertResponseDTO.concertList().get(0).getConcertId());
        assertNotNull(seatResponseDTO);

        // 1번 콘서트의 1번 좌석으로 예약을 진행한다.
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(userId, 1L, 1L, seatResponseDTO.seatList().get(0).cost()); // 예시로 사용
        ReservationResponseDTO reservationResponseDTO = makeReservationUseCase.execute(reservationRequestDTO);
        assertEquals("좌석 예약 성공", reservationResponseDTO.message());
        assertNotNull(reservationResponseDTO.reservation());
    }

    @Test
    @DisplayName("좌석예약 동시성 테스트")
    public void when_reserve_concurrent_issue() throws InterruptedException {
        int threadCount = 10;
        final List<ReservationResponseDTO> result = new ArrayList<>();

        // ExecutorService
        // ThreadPool 구성, Task 실행, 관리의 역할
        // ExecutorService 객체를 생성하며, 쓰레드풀의 개수 및 종류를 지정 가능
        final ExecutorService executorService = Executors.newFixedThreadPool(30);
        // CountDownLatch
        // 어떤 쓰레드가 다른 쓰레드에서 작업이 완료될 때까지 기다릴 수 있도록 해주는 클래스
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            Long testId = (long) i + 10;
            executorService.submit(() -> {
                try {
                    UserRequestDTO userRequestDTO = new UserRequestDTO(testId);
                    QueueResponseDTO queueResponseDTO = enterQueueUseCase.execute(userRequestDTO);
                    result.add(makeReservationUseCase.execute(new ReservationRequestDTO(1L, 1L, testId, 50000L)));
                } catch (Exception e) {
                    result.add(new ReservationResponseDTO(e.getMessage(), null));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        for (ReservationResponseDTO responseDTO : result) {
            System.out.println(responseDTO.message());
        }
        assert result.get(0).message().equals("좌석 예약 성공");
        assert result.get(1).message().equals("해당 좌석은 예약할 수 없습니다.");
        assert result.get(2).message().equals("해당 좌석은 예약할 수 없습니다.");

    }
}
