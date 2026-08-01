package com.eventreserve.service;

import com.eventreserve.dto.ReservationRequestDto;
import com.eventreserve.entity.Reservation;
import com.eventreserve.entity.Seat;
import com.eventreserve.entity.User;
import com.eventreserve.repository.ReservationRepository;
import com.eventreserve.repository.SeatRepository;
import com.eventreserve.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              SeatRepository seatRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public Reservation createReservation(ReservationRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserID())
                .orElseThrow(() -> new RuntimeException("User: " + requestDto.getUserID() + " not found!"));

        Seat seat = seatRepository.findById(requestDto.getSeatID())
                .orElseThrow(() -> new RuntimeException("Seat: " + requestDto.getSeatID() + " not found!"));

        if (seat.isReserved()) {
            throw new IllegalStateException("Seat " + seat.getSeatID() + " is already reserved!");
        }

        seat.setReserved(true);
        seatRepository.save(seat);

        Reservation reservation = Reservation.builder()
                .user(user)
                .seat(seat)
                .reservationTime(LocalDateTime.now())
                .build();

        return reservationRepository.save(reservation);
    }
}