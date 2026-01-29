package com.hostelbooking.app.repository;

import com.hostelbooking.app.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
