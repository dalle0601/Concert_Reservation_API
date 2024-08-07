package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.point.reqeust.PointRequestDTO;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
public class MockController {
    @GetMapping("/mock/cicd")
    public String cicdTest() {
        return """
                {
                    "message": "배포테스트!f1232132141232"
                }
                """;
    }
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
    public String checkUserToken (@PathVariable String userId) {
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
                    "code": "SUCCESS",
                    "result": {
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
                }
                """;
    }

    @GetMapping("/mock/reservation/{concertId}/seat")
    public String getAvailableSeat(@PathVariable Long concertId) {
        return """
                {
                    "code": "SUCCESS",
                    "result": {
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
                }
                """;
    }

    @PostMapping("/mock/reservation")
    public String tempReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        return """
                {
                    "message": "좌석 예약 성공",
                    "reservation": {
                        "reservationId": 1,
                        "userId": 1,
                        "concertId": 1,
                        "seatId": 1,
                        "status": "temporary",
                        "cost": 50000,
                        "reservationTime": "2024-04-18T19:38:34.925159",
                        "expirationTime": "2024-04-18T19:43:34.925177"
                    }
                }
                """;
    }

    @GetMapping("/mock/point/{userId}")
    public String getUserPoint(@PathVariable String userId) {
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

    @PostMapping("/mock/point/payment")
    public String pointPayment(@RequestBody PointRequestDTO pointRequestDTO) {
        return """
                {
                    "message": "결제 완료",
                    "paymentInfoDTO": {
                        "userId": 1,
                        "reservationId": 1,
                        "concertId": 1,
                        "seatId": 2,
                        "cost": 50000,
                        "status": "reserved",
                        "paymentTime": "2024-04-19T13:54:31.740541"
                    }
                }
                """;
    }

}
