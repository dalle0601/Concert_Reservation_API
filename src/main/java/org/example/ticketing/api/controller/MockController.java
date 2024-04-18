package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.request.PointRequestDTO;
import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MockController {
    @PostMapping("/mock/user/queue/enter")
    public String enterQueue (@RequestBody UserRequestDTO userRequestDTO) {
        // {"userId": "test3"}
        return """
                {
                    "message" : "대기중 입니다.",
                    "waitCount" : 5,
                    "token" : null,
                    "expireTime" : null
                }
                """;
    }

    @GetMapping("/mock/user/token/check/{userId}")
    public String checkUserToken (@PathVariable Long userId) {
        return """
                {
                    "message": "유효한 토큰입니다.",
                    "token": "c7de6670-4112-4016-9dad-bf91247ea012",
                    "expiredTime": "2024-04-18T11:37:14.260946"
                }
                """;
    }

    @GetMapping("/mock/reservation/date")
    public String getAvailableDate() {
        return """
                {
                    "message": "이용가능한 콘서트 날짜 조회 성공",
                    "concertList": [
                        {
                            "concertId": 1,
                            "concertTitle": "첫번재 콘서트",
                            "concertDate": "2024-04-19T15:30:00",
                            "maxSeatCnt": 50,
                            "availableSeatCnt": 20,
                            "createdAt": "2024-04-12T15:30:00"
                        },
                        {
                            "concertId": 2,
                            "concertTitle": "두번째 콘서트",
                            "concertDate": "2024-04-22T17:30:00",
                            "maxSeatCnt": 50,
                            "availableSeatCnt": 11,
                            "createdAt": "2024-04-10T15:30:00"
                        }
                    ]
                }
                """;
    }

    @GetMapping("/mock/reservation/{concertId}/seat")
    public String getAvailableSeat(@PathVariable Long concertId) {
        return """
                {
                    "message": "이용가능한 콘서트 좌석 조회 성공",
                    "seatList": [
                        {
                            "seat_id": 1,
                            "seat_number": "A1",
                            "cost": 50000,
                            "seat_status": "available"
                        },
                        {
                            "seat_id": 2,
                            "seat_number": "A2",
                            "cost": 50000,
                            "seat_status": "available"
                        },
                        {
                            "seat_id": 3,
                            "seat_number": "A3",
                            "cost": 50000,
                            "seat_status": "available"
                        },
                        {
                            "seat_id": 23,
                            "seat_number": "A23",
                            "cost": 50000,
                            "seat_status": "available"
                        },
                        {
                            "seat_id": 37,
                            "seat_number": "B12",
                            "cost": 45000,
                            "seat_status": "available"
                        },
                        {
                            "seat_id": 47,
                            "seat_number": "B22",
                            "cost": 45000,
                            "seat_status": "available"
                        },
                    ]
                }
                """;
    }

    @PostMapping("/mock/reservation")
    public String tempReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        return """
                
                """;
    }

    @GetMapping("/mock/point/{userId}")
    public String getUserPoint(@PathVariable Long userId) {
        return """
                {
                    "message": "유저 정보 조회 성공",
                    "userId": 1,
                    "point": 5000
                }
                """;
    }

    @PatchMapping("/mock/point/charge")
    public String pointCharge(@RequestBody PointRequestDTO pointRequestDTO) {
        return """
                {
                    "message": "포인트 충전 성공",
                    "pointHistory": {
                        "pointId": 1,
                        "userId": 1,
                        "point": 1000,
                        "status": "charge",
                        "createdAt": "2024-04-18T15:26:48.55241"
                    }
                }
                """;
    }

//    @PostMapping("/mock/point/payment")
//    public ResponseEntity<PointResponseDTO> pointPayment(@RequestBody PointRequestDTO pointRequestDTO) {
//        LocalDateTime time_now = LocalDateTime.now();
//        PointResponseDTO pointResponseDTO = new PointResponseDTO(pointRequestDTO.userId(), 5000L+pointRequestDTO.point(), time_now);
//
//        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
//    }

}
