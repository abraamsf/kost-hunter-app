package com.example.kost_management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    
    @Column(length = 2000)
    private String description;
    private String facilities;

    // --- PERUBAHAN: DARI SATU FOTO JADI BANYAK ---
    @ElementCollection // Ini akan membuat tabel tambahan otomatis untuk menyimpan list foto
    private List<String> images = new ArrayList<>();

    private Double latitude;
    private Double longitude;

    private Integer availableRooms;

    private String ownerName;
    private String ownerPhone;
    private Long ownerId; 
}
