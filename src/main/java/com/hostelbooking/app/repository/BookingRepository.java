package com.hostelbooking.app.repository;

import com.hostelbooking.app.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
