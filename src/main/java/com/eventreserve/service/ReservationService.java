package com.eventreserve.service;

import com.eventreserve.dto.ReservationRequestDto;
import com.eventreserve.dto.ReservationResponseDto;
import com.eventreserve.entity.Reservation;
import com.eventreserve.entity.Seat;
import com.eventreserve.entity.User;
import com.eventreserve.exception.*;
import com.eventreserve.repository.ReservationRepository;
import com.eventreserve.repository.SeatRepository;
import com.eventreserve.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserID())
                .orElseThrow(() -> new ResourceNotFoundException("User: " + requestDto.getUserID() + " not found!"));

        Seat seat = seatRepository.findById(requestDto.getSeatID())
                .orElseThrow(() -> new ResourceNotFoundException("Seat: " + requestDto.getSeatID() + " not found!"));

        if (seat.isReserved()) {
            throw new SeatIsAlreadyReservedException("Seat " + seat.getSeatID() + " is already reserved!");
        }

        seat.setReserved(true);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSeat(seat);
        reservation.setReservationTime(LocalDateTime.now());

        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponseDto.fromEntity(savedReservation);
    }

    @Transactional
    public void cancelReservation(Long id){
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Reservation " + id + "does not exist!"));

        Seat seat = reservation.getSeat();

        if(seat!=null)
            seat.setReserved(false);

        reservationRepository.delete(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getAllReservations(){
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getReservationsByUserID(Long userID) {
        return reservationRepository.findByUserUserID(userID).stream()
                .map(ReservationResponseDto::fromEntity)
                .toList();
    }

}