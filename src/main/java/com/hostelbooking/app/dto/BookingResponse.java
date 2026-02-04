package com.hostelbooking.app.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonPropertyOrder({
        "bookingId",
        "userName",
        "hostelName",
        "roomNumber",
        "bookingDate",
        "status"
})
public class BookingResponse {

    private Long bookingId;
    private String userName;
    private String hostelName;
    private String roomNumber;
    private LocalDate bookingDate;
    private String status;
}
