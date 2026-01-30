package com.hostelbooking.app.service;

import com.hostelbooking.app.exception.HostelAlreadyExistsException;
import com.hostelbooking.app.model.Hostel;
import com.hostelbooking.app.repository.HostelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostelService {

    private final HostelRepository hostelRepository;

    public Hostel createHostel(Hostel hostel) {
        if (hostelRepository.findByName(hostel.getName()).isPresent()) {
            throw new HostelAlreadyExistsException("Hostel already exists");
        }
        return hostelRepository.save(hostel);
    }

    public List<Hostel> getAllHostels() {
        return hostelRepository.findAll();
    }

    public Hostel getHostelById(Long id) {
        return hostelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found"));
    }
}
