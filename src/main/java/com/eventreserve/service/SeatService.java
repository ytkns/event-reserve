package com.eventreserve.service;

import com.eventreserve.entity.Seat;
import com.eventreserve.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Seat getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono miejsca o ID: " + id));
    }

    @Transactional
    public Seat reserveSeat(Long id) {
        Seat seat = getSeatById(id);

        if (seat.isReserved()) {
            throw new IllegalStateException("Miejsce o ID " + id + " jest już zarezerwowane!");
        }

        seat.setReserved(true);

        return seatRepository.save(seat);
    }
}