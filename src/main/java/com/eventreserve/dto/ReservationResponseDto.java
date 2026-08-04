package com.eventreserve.dto;

import com.eventreserve.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {
    private Long reservationID;
    private Long userID;
    private String username;
    private Long seatID;
    private String seatNumber;
    private LocalDateTime reservationTime;

    public static ReservationResponseDto fromEntity(Reservation reservation) {
        return ReservationResponseDto.builder()
                .reservationID(reservation.getReservationID())
                .userID(reservation.getUser() != null ? reservation.getUser().getUserID() : null)
                .username(reservation.getUser() != null ? reservation.getUser().getUsername() : null)
                .seatID(reservation.getSeat() != null ? reservation.getSeat().getSeatID() : null)
                .seatNumber(reservation.getSeat() != null ? reservation.getSeat().getSeatNumber() : null)
                .reservationTime(reservation.getReservationTime())
                .build();
    }
}
