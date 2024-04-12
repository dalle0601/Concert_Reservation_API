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
    @PostMapping("/mock/user/token")
    public ResponseEntity<UserResponseDTO> issueUserToken (@RequestBody UserRequestDTO userRequestDTO) {
        // {"user_id": "test3"}
        UserResponseDTO userResponseDTO = new UserResponseDTO(userRequestDTO.user_id() + "+UUID+대기열정보");
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/mock/reservation/date")
    public ResponseEntity<List<ConcertResponseDTO>> getAvailableDate() {
        List<ConcertResponseDTO> concertList = new ArrayList<>();
        concertList.add(new ConcertResponseDTO(1L, "첫번째콘서트", LocalDateTime.of(2024, 4, 28, 16, 0), 200L, 17L));
        concertList.add(new ConcertResponseDTO(2L, "두번째콘서트", LocalDateTime.of(2024, 4, 28, 20, 30), 150L, 20L));
        concertList.add(new ConcertResponseDTO(3L, "세번째콘서트", LocalDateTime.of(2024, 5, 8, 17, 0), 210L, 5L));
        concertList.add(new ConcertResponseDTO(4L, "네번째콘서트", LocalDateTime.of(2024, 5, 23, 16, 30), 880L, 112L));

        return new ResponseEntity<>(concertList, HttpStatus.OK);
    }

    @GetMapping("/mock/reservation/{concert_id}/seat")
    public ResponseEntity<List<SeatResponseDTO>> getAvailableSeat(@PathVariable Long concert_id) {
        List<SeatResponseDTO> seatList = new ArrayList<>();
        for(Long i = 0L; i < 3L; i++){
            seatList.add(new SeatResponseDTO( i, "A0"+(i+1), 70000L, "Available"));
        }
        seatList.add(new SeatResponseDTO(116L, "B13", 80000L, "Available"));
        seatList.add(new SeatResponseDTO( 201L, "S05", 110000L, "Available"));


        return new ResponseEntity<>(seatList, HttpStatus.OK);
    }

    @PostMapping("/mock/reservation")
    public ResponseEntity<ReservationResponseDTO> tempReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        // {"concert_id": 3, "seat_id": 116, "cost": 80000, "reservation_time": "2024-04-02T17:30:00"}
        LocalDateTime time_now = LocalDateTime.now();

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO(1L, reservationRequestDTO.concert_id(), 1L, 116L, "Reserved", time_now, time_now.plusMinutes(5));

        return new ResponseEntity<>(reservationResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/mock/point/{user_id}")
    public ResponseEntity<PointResponseDTO> getUserPoint(@PathVariable Long user_id) {
        PointResponseDTO pointResponseDTO = new PointResponseDTO(user_id, 5000L);

        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/mock/point/charge")
    public ResponseEntity<PointResponseDTO> pointCharge(@RequestBody PointRequestDTO pointRequestDTO) {
        LocalDateTime time_now = LocalDateTime.now();
        PointResponseDTO pointResponseDTO = new PointResponseDTO(pointRequestDTO.user_id(), 5000L+pointRequestDTO.point(), time_now);

        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/mock/point/payment")
    public ResponseEntity<PointResponseDTO> pointPayment(@RequestBody PointRequestDTO pointRequestDTO) {
        LocalDateTime time_now = LocalDateTime.now();
        PointResponseDTO pointResponseDTO = new PointResponseDTO(pointRequestDTO.user_id(), 5000L+pointRequestDTO.point(), time_now);

        return new ResponseEntity<>(pointResponseDTO, HttpStatus.OK);
    }

}
