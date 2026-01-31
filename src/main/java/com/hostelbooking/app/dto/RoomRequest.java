package com.hostelbooking.app.dto;

import lombok.Data;

@Data
public class RoomRequest {
    private String roomNumber;
    private int capacity;
    private int availableBeds;
    private Long hostelId;
}
