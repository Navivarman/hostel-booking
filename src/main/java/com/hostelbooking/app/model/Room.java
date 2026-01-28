package com.hostelbooking.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "room_number")
    private String roomNumber;

    @NotNull
    private int capacity;

    @NotNull
    @Column(name = "available_beds")
    private int availableBeds;

    @ManyToOne
    @JoinColumn(name = "hostel_id",nullable = false)
    private Hostel hostel;
}
