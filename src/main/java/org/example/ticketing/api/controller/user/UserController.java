package org.example.ticketing.api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.api.usecase.user.EnterQueueUseCase;
import org.example.ticketing.domain.user.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final EnterQueueUseCase enterQueueUseCase;
    private final CheckTokenUseCase checkTokenUseCase;
    private final TokenService tokenService;

    @Autowired
    public UserController (EnterQueueUseCase enterQueueUseCase, CheckTokenUseCase checkTokenUseCase, TokenService tokenService) {
        this.enterQueueUseCase = enterQueueUseCase;
        this.checkTokenUseCase = checkTokenUseCase;
        this.tokenService = tokenService;
    }
    @Operation(summary = "유저 대기열 진입 요청")
    @PostMapping("/user/queue/enter")
    public ResponseEntity<QueueResponseDTO> enterQueue (@RequestBody UserRequestDTO userRequestDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(enterQueueUseCase.execute(userRequestDTO));
    }
    @Operation(summary = "유저 토큰 확인 요청")
    @GetMapping("/user/token/check/{userId}")
    public ResponseEntity<TokenResponseDTO> checkUserToken (@PathVariable Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(checkTokenUseCase.execute(new UserRequestDTO(userId)));
    }
}
