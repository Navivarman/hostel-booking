package com.hostelbooking.app.controller;

import com.hostelbooking.app.dto.RoomRequest;
import com.hostelbooking.app.model.Room;
import com.hostelbooking.app.repository.RoomRepository;
import com.hostelbooking.app.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRooms(@RequestBody RoomRequest roomRequest){
        Room room = roomService.createRooms(roomRequest);
        return new ResponseEntity<>(room,HttpStatus.CREATED);
    }
}
