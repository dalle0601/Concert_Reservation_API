package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.IssueUserTokenUseCase;
import org.example.ticketing.api.usecase.users.IssueUserTokenUseCaseTest;
import org.example.ticketing.domain.user.service.TokenService;
import org.example.ticketing.domain.user.service.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    @Mock
    private IssueUserTokenUseCase issueUserTokenUseCase;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @DisplayName("userToken 발급 테스트")
    @Test
    public void issueTokenTest() throws Exception {
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO("abc-def-ghi/onGoing");
        when(issueUserTokenUseCase.execute(any())).thenReturn(tokenResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"user_id\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(tokenResponseDTO.token()));
    }

    @DisplayName("userToken 대기열 조회 테스트")
    @Test
    public void confirmTokenTest() throws Exception {
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO("abc-def-ghi/onGoing");

        when(tokenService.checkToken(any())).thenReturn(tokenResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/token/check/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(tokenResponseDTO.token()));
    }
}
