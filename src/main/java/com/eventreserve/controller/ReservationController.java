package com.eventreserve.controller;

import com.eventreserve.dto.ReservationRequestDto;
import com.eventreserve.entity.Reservation;
import com.eventreserve.service.ReservationService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationRequestDto requestDto) {
        return reservationService.createReservation(requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}