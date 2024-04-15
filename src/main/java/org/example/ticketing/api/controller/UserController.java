package org.example.ticketing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.request.TokenRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.IssueUserTokenUseCase;
import org.example.ticketing.domain.user.service.TokenService;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final IssueUserTokenUseCase issueUserTokenUseCase;
    private final TokenService tokenService;

    @Autowired
    public UserController (IssueUserTokenUseCase issueUserTokenUseCase, TokenService tokenService) {
        this.issueUserTokenUseCase = issueUserTokenUseCase;
        this.tokenService = tokenService;
    }
    @Operation(summary = "유저 토큰 발급")
    @PostMapping("/user/token")
    public ResponseEntity<TokenResponseDTO> issueUserToken (@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(issueUserTokenUseCase.execute(userRequestDTO));
    }
    @Operation(summary = "유저 토큰 대기열 체크")
    @GetMapping("/user/token/check/{userId}")
    public ResponseEntity<TokenResponseDTO> checkUserToken (@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.checkToken(new UserRequestDTO(userId)));
    }
}
