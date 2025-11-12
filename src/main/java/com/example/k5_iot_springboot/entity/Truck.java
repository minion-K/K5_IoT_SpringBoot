package com.example.k5_iot_springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "trucks")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Truck {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 트럭을 운영하는 사용자ID
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="owner_id", nullable = false,
            foreignKey = @ForeignKey(name="fk_trucks_user")
    )
    private F_User user;

    @Column(nullable = false, length = 100)
    private String name; // 트럭 이름

    @Column(length = 50)
    private String category;

    @Column(length = 50)
    private String region;

    @Column(length = 255)
    private String description;

    @OneToMany(mappedBy = "truck", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setTruck(this);
    }
}
