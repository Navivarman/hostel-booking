package com.hostelbooking.app.service;

import com.hostelbooking.app.BookingStatus;
import com.hostelbooking.app.dto.BookingResponse;
import com.hostelbooking.app.exception.ApiException;
import com.hostelbooking.app.model.Booking;
import com.hostelbooking.app.model.Room;
import com.hostelbooking.app.model.User;
import com.hostelbooking.app.repository.BookingRepository;
import com.hostelbooking.app.repository.RoomRepository;
import com.hostelbooking.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingResponse requestBooking(Long userId, Long hostelId, Long roomId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ApiException("Room not found"));

        // ✅ Validate hostel-room relation
        if (!room.getHostel().getId().equals(hostelId)) {
            throw new ApiException("Room does not belong to this hostel");
        }

        // ✅ Validate availability
        if (room.getAvailableBeds() <= 0) {
            throw new ApiException("No beds available");
        }

        Booking booking = Booking.builder()
                .user(user)
                .room(room)
                .bookingDate(LocalDate.now())
                .status(BookingStatus.PENDING)
                .build();

        bookingRepository.save(booking);

        return mapToBookingResponse(booking);
    }



    public BookingResponse approveBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException("Booking not found"));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new ApiException("Booking already processed");
        }

        Room room = booking.getRoom();

        if (room.getAvailableBeds() <= 0) {
            throw new ApiException("No beds available");
        }

        // decrease bed count
        room.setAvailableBeds(room.getAvailableBeds() - 1);
        roomRepository.save(room);

        booking.setStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking);

        return mapToBookingResponse(booking);
    }


    public BookingResponse rejectBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException("Booking not found"));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new ApiException("Booking already processed");
        }

        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);

        return mapToBookingResponse(booking);
    }


    private BookingResponse mapToBookingResponse(Booking booking) {

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .userName(booking.getUser().getName())
                .hostelName(booking.getRoom().getHostel().getName())
                .roomNumber(booking.getRoom().getRoomNumber())
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus().name())
                .build();
    }

}

