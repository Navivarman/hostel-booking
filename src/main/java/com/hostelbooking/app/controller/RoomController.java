package com.hostelbooking.app.controller;

import com.hostelbooking.app.dto.RoomRequest;
import com.hostelbooking.app.model.Room;
import com.hostelbooking.app.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRooms(@RequestBody RoomRequest roomRequest){
        Room room = roomService.createRooms(roomRequest);
        return new ResponseEntity<>(room,HttpStatus.CREATED);
    }

    @GetMapping("/hostel/{hostelId}/rooms")
    public ResponseEntity<List<Room>> getAllRoomsByHostelId(@PathVariable Long hostelId){
        return new ResponseEntity<>(roomService.getRoomsHostelById(hostelId),HttpStatus.OK);
    }

    @GetMapping("/hostel/{hostelId}/rooms/available")
    public ResponseEntity<List<Room>> getAllRoomsAvailableByHostelId(@PathVariable Long hostelId){
        return ResponseEntity.ok(roomService.getAvailableRoomsByHostel(hostelId));
    }
}
