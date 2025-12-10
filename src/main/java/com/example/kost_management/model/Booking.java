package com.example.kost_management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd") 
    private LocalDate startDate;
    
    private String status;

    // --- PENANDA KOST MANA YANG DIPESAN ---
    private Long kostId;
}