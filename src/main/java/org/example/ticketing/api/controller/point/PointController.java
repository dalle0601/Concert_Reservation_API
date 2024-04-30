package org.example.ticketing.api.controller.point;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.point.reqeust.PaymentRequestDTO;
import org.example.ticketing.api.dto.point.reqeust.PointRequestDTO;
import org.example.ticketing.api.dto.point.response.PaymentResponseDTO;
import org.example.ticketing.api.dto.point.response.PointHistorySaveResponseDTO;
import org.example.ticketing.api.dto.point.response.PointResponseDTO;
import org.example.ticketing.api.usecase.point.ChargePointUseCase;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.api.usecase.point.PaymentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PointController {
    private final GetPointUseCase getPointUseCase;
    private final ChargePointUseCase chargePointUseCase;
    private final PaymentUseCase paymentUseCase;
    public PointController(GetPointUseCase getPointUseCase, ChargePointUseCase chargePointUseCase, PaymentUseCase paymentUseCase) {
        this.getPointUseCase = getPointUseCase;
        this.chargePointUseCase = chargePointUseCase;
        this.paymentUseCase = paymentUseCase;
    }


    @Operation(summary = "포인트 잔액 조회")
    @GetMapping("/point/{userId}")
    public ResponseEntity<PointResponseDTO> getUserPoint(@PathVariable Long userId) {
        PointResponseDTO pointResponseDTO = getPointUseCase.execute(userId);
        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "포인트 충전")
    @PatchMapping("/point/charge")
    public ResponseEntity<PointHistorySaveResponseDTO> userPointCharge(@RequestBody PointRequestDTO pointRequestDTO) {
        PointHistorySaveResponseDTO pointHistorySaveResponseDTO = chargePointUseCase.execute(pointRequestDTO);
        return new ResponseEntity<>(pointHistorySaveResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "결제")
    @PostMapping("/point/payment")
    public ResponseEntity<PaymentResponseDTO> pointPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO paymentResponseDTO = paymentUseCase.execute(paymentRequestDTO);
        return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
    }
}
