package com.eventreserve.service;

import com.eventreserve.entity.Reservation;
import com.eventreserve.entity.Seat;
import com.eventreserve.entity.User;
import com.eventreserve.exception.SeatIsAlreadyReservedException;
import com.eventreserve.exception.ResourceNotFoundException;
import com.eventreserve.exception.ReservationNotFoundException;
import com.eventreserve.dto.ReservationResponseDto;
import com.eventreserve.dto.ReservationRequestDto;
import com.eventreserve.repository.ReservationRepository;
import com.eventreserve.repository.SeatRepository;
import com.eventreserve.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ReservationService reservationService;

    private User user;
    private Seat seat;
    private ReservationRequestDto requestDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserID(1L);
        user.setUsername("Sam Smith");

        seat = new Seat();
        seat.setSeatID(2L);
        seat.setSeatNumber("A-2");
        seat.setReserved(false);

        requestDto = new ReservationRequestDto(1L, 2L);
    }

    @Test
    @DisplayName("Creating reservation - success")
    void createReservation_Success(){
        Reservation savedReservation = new Reservation();
        savedReservation.setReservationID(3L);
        savedReservation.setReservationTime(LocalDateTime.now());
        savedReservation.setUser(user);
        savedReservation.setSeat(seat);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(seatRepository.findById(2L)).thenReturn(Optional.of(seat));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        ReservationResponseDto result = reservationService.createReservation(requestDto);

        assertNotNull(result);
        assertEquals(3L, result.getReservationID());
        assertTrue(seat.isReserved());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    @DisplayName("Creating reservation - error: seat is already reserved")
    void createReservation_SeatAlreadyReserved(){
        seat.setReserved(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(seatRepository.findById(2L)).thenReturn(Optional.of(seat));

        assertThrows(SeatIsAlreadyReservedException.class, () -> reservationService.createReservation(requestDto));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Creating reservation - error: user not found")
    void createReservation_UserNotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservationService.createReservation(requestDto));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Canceling reservation - success")
    void cancelReservation_Success(){
        seat.setReserved(true);
        Reservation reservation = new Reservation();
        reservation.setReservationID(3L);
        reservation.setUser(user);
        reservation.setSeat(seat);

        when(reservationRepository.findById(3L)).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation(3L);

        assertFalse(seat.isReserved());
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    @DisplayName("Canceling reservation - error: reservation does not exist")
    void cancelReservation_NotFound() {
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> reservationService.cancelReservation(99L));
        verify(reservationRepository, never()).delete(any());
    }
}
