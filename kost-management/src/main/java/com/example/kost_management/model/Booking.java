package com.example.kost_management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat; // <--- WAJIB IMPORT INI

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String kostName;
    private Double totalPrice;
    private Integer durationInMonths;

    // --- PERBAIKAN PENTING DI SINI ---
    // Memberi tahu Java format tanggalnya adalah Tahun-Bulan-Tanggal
    @JsonFormat(pattern = "yyyy-MM-dd") 
    private LocalDate startDate;
    
    private String status; 
}