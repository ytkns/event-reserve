package com.eventreserve.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {

    @NotNull(message = "userID field is mandatory")
    @Positive(message = "userID must be a positive number")
    private Long userID;

    @NotNull(message = "seatID field is mandatory")
    @Positive(message = "seatID must be a positive number")
    private Long seatID;
}
