package org.example.ticketing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.request.PointRequestDTO;
import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class PointController {
    @Operation(summary = "포인트 잔액 조회")
    @GetMapping("/point/{user_id}")
    public ResponseEntity<PointResponseDTO> getUserPoint(@PathVariable Long user_id) {
        PointResponseDTO pointResponseDTO = new PointResponseDTO(user_id, 5000L);

        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "포인트 충전")
    @PatchMapping("/point/charge")
    public ResponseEntity<PointResponseDTO> userPointCharge(@RequestBody PointRequestDTO pointRequestDTO) {
        LocalDateTime time_now = LocalDateTime.now();
        PointResponseDTO pointResponseDTO = new PointResponseDTO(pointRequestDTO.user_id(), 5000L+pointRequestDTO.point(), time_now);

        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "결제")
    @PostMapping("/point/payment")
    public ResponseEntity<PointResponseDTO> pointPayment(@RequestBody PointRequestDTO pointRequestDTO) {
        LocalDateTime time_now = LocalDateTime.now();
        PointResponseDTO pointResponseDTO = new PointResponseDTO(pointRequestDTO.user_id(), 5000L+pointRequestDTO.point(), time_now);

        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
    }
}
