package com.eventreserve.config;

import com.eventreserve.entity.Seat;
import com.eventreserve.entity.User;
import com.eventreserve.repository.SeatRepository;
import com.eventreserve.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    public DataInitializer(SeatRepository seatRepository, UserRepository userRepository) {
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        User user = User.builder()
                .username("Will Smith")
                .email("will.smith@example.com")
                .build();

        userRepository.save(user);

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

        seatRepository.saveAll(List.of(seat1, seat2, seat3));

        System.out.println("Successfully loaded users and seats data!");
    }
}