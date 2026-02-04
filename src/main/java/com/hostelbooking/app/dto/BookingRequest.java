package com.hostelbooking.app.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long userId;
    private Long hostelId;
    private Long roomId;
}
