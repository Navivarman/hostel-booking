package com.hostelbooking.app.repository;

import com.hostelbooking.app.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelRepository extends JpaRepository<Hostel,Long> {
}
