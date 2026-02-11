package com.hostelbooking.app.controller;

import com.hostelbooking.app.dto.BookingRequest;
import com.hostelbooking.app.dto.BookingResponse;
import com.hostelbooking.app.model.Booking;
import com.hostelbooking.app.model.User;
import com.hostelbooking.app.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

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
    @PreAuthorize("hasRole('ADMIN')")
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


    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByUser(userId)
        );
    }


    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {

        User loggedInUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(
                bookingService.getBookingsByUser(loggedInUser.getId())
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<List<BookingResponse>> getPendingBookings() {
        return ResponseEntity.ok(
                bookingService.getPendingBookings()
        );
    }

    @PutMapping("/{bookingId}/cancel")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {

        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            User loggedInUser = (User) authentication.getPrincipal();

            return ResponseEntity.ok(
                    bookingService.cancelBooking(
                            bookingId,
                            loggedInUser
                    )
            );

        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }


}


