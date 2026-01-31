package com.hostelbooking.app.service;

import com.hostelbooking.app.dto.RoomRequest;
import com.hostelbooking.app.model.Hostel;
import com.hostelbooking.app.model.Room;
import com.hostelbooking.app.repository.HostelRepository;
import com.hostelbooking.app.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final HostelRepository hostelRepository;
    private final RoomRepository roomRepository;

    public Room createRooms(RoomRequest request){
        Hostel hostel = hostelRepository.findById(request.getHostelId())
                .orElseThrow(() -> new RuntimeException("Hostel not found!"));
        Room room = Room.builder()
                .roomNumber(request.getRoomNumber())
                .capacity(request.getCapacity())
                .availableBeds(request.getAvailableBeds())
                .hostel(hostel).build();

        return roomRepository.save(room);
    }
}
