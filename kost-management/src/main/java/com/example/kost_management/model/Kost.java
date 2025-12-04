package com.example.kost_management.model;

import jakarta.persistence.*; // Pastikan ini ada
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    
    @Column(length = 1000)
    private String description;
    private String facilities;
    private String imageUrl;

    private Double latitude;
    private Double longitude;
}