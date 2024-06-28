package org.example.ticketing.api.controller.point;

import io.swagger.v3.oas.annotations.Operation;
import org.example.ticketing.api.dto.common.Response;
import org.example.ticketing.api.dto.point.reqeust.PaymentRequestDTO;
import org.example.ticketing.api.dto.point.reqeust.PointRequestDTO;
import org.example.ticketing.api.dto.point.response.PaymentResponseDTO;
import org.example.ticketing.api.dto.point.response.PointHistorySaveResponseDTO;
import org.example.ticketing.api.dto.point.response.PointResponseDTO;
import org.example.ticketing.api.usecase.point.ChargePointUseCase;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.api.usecase.point.PaymentUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
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
    public Response<PointResponseDTO> getUserPoint(@PathVariable Long userId) {
        return Response.success(getPointUseCase.execute(userId));
    }

    @Operation(summary = "포인트 충전")
    @PatchMapping("/point/charge")
    public Response<PointHistorySaveResponseDTO> userPointCharge(@RequestBody PointRequestDTO pointRequestDTO) {
        return Response.success(chargePointUseCase.execute(pointRequestDTO));
    }

    @Operation(summary = "결제")
    @PostMapping("/point/payment")
    public Response<PaymentResponseDTO> pointPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        return Response.success(paymentUseCase.execute(paymentRequestDTO));
    }
}
