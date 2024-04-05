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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(UserController.class)
public class UserInfoControllerTest {
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

    @DisplayName("userToken 발급 테스트")
    @Test
    public void testIssueToken() throws Exception {
        Long user_id = 1L;
        when(userService.issueToken(any(UserRequestDTO.class))).thenReturn("generatedToken123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"user_id\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("generatedToken123"));
    }
}
