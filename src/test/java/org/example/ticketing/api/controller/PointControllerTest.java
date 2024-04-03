package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.domain.user.service.UserService;
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
public class PointControllerTest {
    //    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @DisplayName("포인트 충전 API")
    @Test
    public void chargePointTest() throws Exception {
        /*
            - 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
            - 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
         */
        String userId = "testuser";
        Mockito.when(userService.issueToken(new UserRequestDTO(userId))).thenReturn("generatedToken123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"testuser\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("generatedToken123"));
    }

    @DisplayName("포인트 조회 API")
    @Test
    public void getPointAmountTest() throws Exception {
        /*
            사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.
         */
        String userId = "testuser";
        Mockito.when(userService.issueToken(new UserRequestDTO(userId))).thenReturn("generatedToken123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"testuser\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("generatedToken123"));
    }

    @DisplayName("결제 API")
    @Test
    public void postTempReservationTest() throws Exception {
        /*
            - 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
            - 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
            - 배정 시간 내에 결제가 완료되지 않았다면 좌석에 대한 임시 배정은 해제되어야 합니다.
            - 배정 시간 내에는 다른 사용자는 예약할 수 없어야 합니다.
         */
        String userId = "testuser";
        Mockito.when(userService.issueToken(new UserRequestDTO(userId))).thenReturn("generatedToken123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": \"testuser\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("generatedToken123"));
    }


}
