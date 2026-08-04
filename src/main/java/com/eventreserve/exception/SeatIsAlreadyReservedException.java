package com.eventreserve.exception;

public class SeatIsAlreadyReservedException extends RuntimeException {
    public SeatIsAlreadyReservedException(String message){
        super(message);
    }
}
