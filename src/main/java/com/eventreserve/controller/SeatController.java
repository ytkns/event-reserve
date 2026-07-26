package com.eventreserve.controller;

import com.eventreserve.entity.Seat;
import com.eventreserve.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }

    @PostMapping("/{id}/reserve")
    public Seat reserveSeat(@PathVariable Long id) {
        return seatService.reserveSeat(id);
    }
}