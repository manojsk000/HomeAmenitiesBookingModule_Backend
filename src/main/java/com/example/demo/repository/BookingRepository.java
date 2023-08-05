package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.module.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	@Query("SELECT b FROM Booking b WHERE b.facility.id = :facilityId AND b.date = :date " +
            "AND ((b.startTime <= :startTime AND b.endTime >= :startTime) OR " +
            "(b.startTime >= :startTime AND b.startTime < :endTime))")
    List<Booking> findConflictingBookings(@Param("facilityId") Long facilityId,
                                          @Param("date") LocalDate date,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime);

}
