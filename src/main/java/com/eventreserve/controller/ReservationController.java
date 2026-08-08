package com.eventreserve.controller;

import com.eventreserve.dto.ReservationRequestDto;
import com.eventreserve.dto.ReservationResponseDto;
import com.eventreserve.service.ReservationService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationResponseDto createReservation(@Valid @RequestBody ReservationRequestDto requestDto) {
        return reservationService.createReservation(requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<ReservationResponseDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/user/{userID}")
    public List<ReservationResponseDto> getReservationsByUserID(@PathVariable Long userID) {
        return reservationService.getReservationsByUserID(userID);
    }
}