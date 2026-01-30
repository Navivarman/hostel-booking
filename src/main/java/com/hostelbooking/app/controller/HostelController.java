package com.hostelbooking.app.controller;

import com.hostelbooking.app.model.Hostel;
import com.hostelbooking.app.service.HostelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hostels")
@RequiredArgsConstructor
public class HostelController {

    private final HostelService hostelService;

    // CREATE HOSTEL
    @PostMapping
    public ResponseEntity<Hostel> createHostel(@Valid @RequestBody Hostel hostel) {
        Hostel saved = hostelService.createHostel(hostel);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET ALL HOSTELS
    @GetMapping
    public ResponseEntity<List<Hostel>> getAllHostels() {
        return ResponseEntity.ok(hostelService.getAllHostels());
    }

    // GET HOSTEL BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Hostel> getHostelById(@PathVariable Long id) {
        return ResponseEntity.ok(hostelService.getHostelById(id));
    }
}
