package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.request.TokenRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.domain.user.service.TokenService;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public UserController (UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/user/token")
    public ResponseEntity<TokenResponseDTO> issueUserToken (@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.issueToken(userRequestDTO));
    }

    @GetMapping("/user/token/check/{userId}")
    public ResponseEntity<TokenResponseDTO> checkUserToken (@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.checkToken(new UserRequestDTO(userId)));
    }
}
