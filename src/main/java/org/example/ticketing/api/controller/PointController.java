package org.example.ticketing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.request.PointRequestDTO;
import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class PointController {
    private final GetPointUseCase getPointUseCase;

    public PointController(GetPointUseCase getPointUseCase) {
        this.getPointUseCase = getPointUseCase;
    }


    @Operation(summary = "포인트 잔액 조회")
    @GetMapping("/point/{userId}")
    public ResponseEntity<PointResponseDTO> getUserPoint(@PathVariable Long userId) {
        PointResponseDTO pointResponseDTO = getPointUseCase.execute(userId);
        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "포인트 충전")
    @PatchMapping("/point/charge")
    public ResponseEntity<PointResponseDTO> userPointCharge(@RequestBody PointRequestDTO pointRequestDTO) {
//        LocalDateTime time_now = LocalDateTime.now();
//        PointResponseDTO pointResponseDTO = new PointResponseDTO(pointRequestDTO.user_id(), 5000L+pointRequestDTO.point(), time_now);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

//    @Operation(summary = "결제")
//    @PostMapping("/point/payment")
//    public ResponseEntity<PointResponseDTO> pointPayment(@RequestBody PointRequestDTO pointRequestDTO) {
//        LocalDateTime time_now = LocalDateTime.now();
//        PointResponseDTO pointResponseDTO = new PointResponseDTO(pointRequestDTO.user_id(), 5000L+pointRequestDTO.point(), time_now);
//
//        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
//    }
}
