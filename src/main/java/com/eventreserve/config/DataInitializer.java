package com.eventreserve.config;

import com.eventreserve.entity.Seat;
import com.eventreserve.repository.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SeatRepository seatRepository;

    // Iniekcja zależności przez konstruktor (dobre praktyki!)
    public DataInitializer(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public void run(String... args) {
        // Tworzymy przykładowe miejsca za pomocą wygenerowanego przez Lombok Builder-a
        Seat seat1 = Seat.builder()
                .seatNumber("A-1")
                .price(new BigDecimal("120.00"))
                .isReserved(false)
                .build();

        Seat seat2 = Seat.builder()
                .seatNumber("A-2")
                .price(new BigDecimal("120.00"))
                .isReserved(false)
                .build();

        Seat seat3 = Seat.builder()
                .seatNumber("B-1")
                .price(new BigDecimal("90.00"))
                .isReserved(true)
                .build();

        // Zapisujemy listę miejsc w bazie danych
        seatRepository.saveAll(List.of(seat1, seat2, seat3));

        System.out.println("✅ Pomyślnie załadowano wstępne dane do bazy!");
    }
}