package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {
    //    @Autowired
    private MockMvc mockMvc;


    @DisplayName("예약 가능 날짜 조회 API")
    @Test
    public void getAvailableDate() throws Exception {
        /*
            대기열 토큰이 검증 됐다는 전제 하에
            예약 가능한 날짜 목록을 조회할 수 있습니다.
         */

    }

    @DisplayName("예약 가능 좌석 조회 API")
    @Test
    public void getAvailableSeat() throws Exception {
        /*
            날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.
            좌석 정보는 1 ~ 50 까지의 좌석번호로 관리됩니다.
         */

    }

    @DisplayName("좌석 예약 요청 API")
    @Test
    public void postTempReservationTest() throws Exception {
        /*
            - 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
            - 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
            - 배정 시간 내에 결제가 완료되지 않았다면 좌석에 대한 임시 배정은 해제되어야 합니다.
            - 배정 시간 내에는 다른 사용자는 예약할 수 없어야 합니다.
         */

    }


}
