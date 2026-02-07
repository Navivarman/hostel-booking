package com.hostelbooking.app.controller;

import com.hostelbooking.app.dto.BookingRequest;
import com.hostelbooking.app.dto.BookingResponse;
import com.hostelbooking.app.model.Booking;
import com.hostelbooking.app.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // User requests booking
    @PostMapping
    public ResponseEntity<BookingResponse> requestBooking(
            @RequestBody BookingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.requestBooking(
                        request.getUserId(),
                        request.getHostelId(),
                        request.getRoomId()
                ));
    }

    // Admin approves booking
    @PutMapping("/{bookingId}/approve")
    public ResponseEntity<?> approveBooking(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok(bookingService.approveBooking(bookingId));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }


    @PutMapping("/{bookingId}/reject")
    public ResponseEntity<?> rejectBooking(@PathVariable Long bookingId) {
        try{
            return ResponseEntity.ok(bookingService.rejectBooking(bookingId));
        }catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByUser(userId)
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<List<BookingResponse>> getPendingBookings() {
        return ResponseEntity.ok(
                bookingService.getPendingBookings()
        );
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(
            @PathVariable Long bookingId,
            @RequestParam Long userId) {

        try {
            return ResponseEntity.ok(
                    bookingService.cancelBooking(bookingId, userId)
            );
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

}


