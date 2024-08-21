package org.example.ticketing.api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.common.Response;
import org.example.ticketing.api.dto.reservation.response.ReservationListResponseDTO;
import org.example.ticketing.api.dto.user.request.UserJoinRequestDTO;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.UserJoinUseCase;
import org.example.ticketing.api.usecase.user.UserReservationUseCase;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.api.usecase.user.EnterQueueUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final EnterQueueUseCase enterQueueUseCase;
    private final CheckTokenUseCase checkTokenUseCase;
    private final UserReservationUseCase userReservationUseCase;
    private final UserJoinUseCase userJoinUseCase;

    @Autowired
    public UserController (EnterQueueUseCase enterQueueUseCase, CheckTokenUseCase checkTokenUseCase, UserReservationUseCase userReservationUseCase, UserJoinUseCase userJoinUseCase) {
        this.enterQueueUseCase = enterQueueUseCase;
        this.checkTokenUseCase = checkTokenUseCase;
        this.userReservationUseCase = userReservationUseCase;
        this.userJoinUseCase = userJoinUseCase;
    }

    @PostMapping("/join")
    public Response<String> loginUser (@RequestBody UserJoinRequestDTO userJoinRequestDTO) {
        return Response.success(userJoinUseCase.execute(userJoinRequestDTO));
    }

    @Operation(summary = "유저 대기열 진입 요청")
    @PostMapping("/token")
    public ResponseEntity<QueueResponseDTO> enterQueue (@RequestBody UserRequestDTO userRequestDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(enterQueueUseCase.execute(userRequestDTO));
    }
    @Operation(summary = "유저 토큰 확인 요청")
    @GetMapping("/{userId}/token")
    public ResponseEntity<TokenResponseDTO> checkUserToken (@PathVariable String userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(checkTokenUseCase.execute(new UserRequestDTO(userId)));
    }

    @Operation(summary = "유저 예약 내역 요청")
    @GetMapping("/{userId}/reservations")
    public Response<ReservationListResponseDTO> getUserReservation (@PathVariable(value="userId") String userId) throws Exception {
        return Response.success(userReservationUseCase.execute(new UserRequestDTO(userId)));
    }
}
