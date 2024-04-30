package org.example.ticketing.api.controller;

import org.example.ticketing.api.controller.user.UserController;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.api.usecase.user.EnterQueueUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    @Mock
    private EnterQueueUseCase enterQueueUseCase;
    @Mock
    private CheckTokenUseCase checkTokenUseCase;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @DisplayName("유저 대기열 진입 요청 테스트")
    @Test
    public void enterQueueTest() throws Exception {
        QueueResponseDTO queueResponseDTO = new QueueResponseDTO("대기중입니다.", 19L, null, null);
        when(enterQueueUseCase.execute(any())).thenReturn(queueResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/queue/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("대기중입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.waitCount").value(19L));
    }

    @DisplayName("유저 토큰 확인 요청")
    @Test
    public void confirmTokenTest() throws Exception {
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO("유효한 토큰입니다.", "abcd-efgh-ijkl", LocalDateTime.now().plusMinutes(5));

        when(checkTokenUseCase.execute(any())).thenReturn(tokenResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/token/check/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("유효한 토큰입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("abcd-efgh-ijkl"));
    }
}
