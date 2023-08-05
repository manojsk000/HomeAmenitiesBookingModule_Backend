package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.example.demo.module.Booking;
import com.example.demo.module.Facility;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.FacilityRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/facilities")
    public String addFacility(@RequestBody Facility facility) {
        facilityRepository.save(facility);
        return "Facility added successfully";
    }

    @PostMapping
    public String bookFacility(@RequestBody Booking booking) {
        Facility facility = facilityRepository.findByName(booking.getFacility().getName());
        if (facility == null) {
            return "Invalid facility name";
        }

        LocalTime startTime = booking.getStartTime();
        LocalTime endTime = booking.getEndTime();
        LocalDate date = booking.getDate();

        // Check if the facility is already booked for the requested time
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(facility.getId(), date, startTime, endTime);
        if (!conflictingBookings.isEmpty()) {
            return "Booking failed, facility already booked";
        }

        // Calculate the total cost based on the facility rates and the booking duration
        int totalCost;
        switch (booking.getFacility().getName().toLowerCase()) {
            case "clubhouse":
                totalCost = calculateClubhouseCost(startTime, endTime);
                break;
            case "tennis court":
                totalCost = calculateTennisCourtCost(startTime, endTime);
                break;
            default:
                return "Invalid facility name";
        }

        booking.setCost(totalCost);
        booking.setFacility(facility);
        bookingRepository.save(booking);
        return "Booking successful, Rs. " + booking.getCost();
    }

    private int calculateClubhouseCost(LocalTime startTime, LocalTime endTime) {
        int durationInHours = (int) startTime.until(endTime, ChronoUnit.HOURS);
        int rate;
        if (startTime.isBefore(LocalTime.of(16, 0)) && endTime.isAfter(LocalTime.of(10, 0))) {
            rate = 100; // Rs. 100 per hour
        } else {
            rate = 500; // Rs. 500 per hour
        }
        return durationInHours * rate;
    }

    private int calculateTennisCourtCost(LocalTime startTime, LocalTime endTime) {
        int durationInHours = (int) startTime.until(endTime, ChronoUnit.HOURS);
        return durationInHours * 50; // Rs. 50 per hour
    }
}

