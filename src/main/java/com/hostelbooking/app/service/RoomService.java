package com.hostelbooking.app.service;

import com.hostelbooking.app.dto.RoomRequest;
import com.hostelbooking.app.model.Hostel;
import com.hostelbooking.app.model.Room;
import com.hostelbooking.app.repository.HostelRepository;
import com.hostelbooking.app.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

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

    public List<Room> getRoomsHostelById(Long hostelId){
        return roomRepository.findByHostelId(hostelId);
    }

    public List<Room> getAvailableRoomsByHostel(Long hostelId){
        return roomRepository.findByHostelIdAndAvailableBedsGreaterThan(hostelId,0);
    }

    public void reduceAvailableBeds(Room room){
        if(room.getAvailableBeds() <= 0){
            throw new RuntimeException("No beds available in this room");
        }
        room.setAvailableBeds(room.getAvailableBeds() - 1);
        roomRepository.save(room);
    }

    public void increaseAvailableBeds(Room room){
        if(room.getAvailableBeds() == room.getCapacity()){
            throw new RuntimeException("More than capacity of beds increase");
        }
        room.setAvailableBeds(room.getAvailableBeds() + 1);
        roomRepository.save(room);
    }
}
