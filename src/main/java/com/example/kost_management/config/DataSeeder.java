package com.example.kost_management.config;

import com.example.kost_management.model.Kost;
import com.example.kost_management.model.User;
import com.example.kost_management.repository.KostRepository;
import com.example.kost_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(KostRepository kostRepo, UserRepository userRepo) {
        return args -> {
            // 1. Cek User
            if (userRepo.count() == 0) {
                userRepo.save(new User(null, "Pemilik Kost (Admin)", "admin@kost.com", "admin123", "ADMIN"));
                userRepo.save(new User(null, "Anak Kost (User)", "user@kost.com", "user123", "USER"));
                System.out.println(">>> User Dummy Created <<<");
            }

            // 2. Cek Data Kost
            if (kostRepo.count() == 0) {
                // PERBAIKAN: Gunakan List.of("url") untuk membungkus gambar
                
                kostRepo.save(new Kost(
                    null,
                    "Kost Dago Asri",
                    2500000.0,
                    "Kost eksklusif dekat ITB.",
                    "AC, WiFi, KM Dalam",
                    List.of("https://images.unsplash.com/photo-1522771753035-4850d355d242?w=600"),
                    -6.884784,
                    107.613580,
                    10,
                    "Admin Dummy",
                    "0800-DUMMY",
                    1L
                ));
                
                kostRepo.save(new Kost(
                    null,
                    "Wisma Dipatiukur",
                    1500000.0,
                    "Dekat UNPAD DU.",
                    "Kasur, WiFi, Dapur",
                    List.of("https://images.unsplash.com/photo-1596296377289-49d7f474024b?w=600"),
                    -6.892463,
                    107.617637,
                    8,
                    "Admin Dummy",
                    "0800-DUMMY",
                    1L
                ));
                
                kostRepo.save(new Kost(
                    null,
                    "Purple House Pasteur",
                    1850000.0,
                    "Dekat Maranatha.",
                    "AC, TV, Security",
                    List.of("https://images.unsplash.com/photo-1555854877-bab0e564b8d5?w=600"),
                    -6.888365,
                    107.575496,
                    12,
                    "Admin Dummy",
                    "0800-DUMMY",
                    1L
                ));
                
                kostRepo.save(new Kost(
                    null,
                    "Pondok Ceria Sukabirus",
                    950000.0,
                    "Murah Telkom Univ.",
                    "Kasur Busa, WiFi",
                    List.of("https://images.unsplash.com/photo-1621293954908-907159247fc8?w=600"),
                    -6.974421,
                    107.630489,
                    6,
                    "Admin Dummy",
                    "0800-DUMMY",
                    1L
                ));
                
                kostRepo.save(new Kost(
                    null,
                    "Kost Braga City",
                    3500000.0,
                    "Pusat Kota.",
                    "Full Furnished, Gym",
                    List.of("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=600"),
                    -6.917464,
                    107.609536,
                    15,
                    "Admin Dummy",
                    "0800-DUMMY",
                    1L
                ));
                
                kostRepo.save(new Kost(
                    null,
                    "Kost Putri Gegerkalong",
                    1100000.0,
                    "Dekat UPI.",
                    "WiFi, Mesin Cuci",
                    List.of("https://images.unsplash.com/photo-1598928506311-c55ded91a20c?w=600"),
                    -6.863775,
                    107.594738,
                    7,
                    "Admin Dummy",
                    "0800-DUMMY",
                    1L
                ));
                
                kostRepo.save(new Kost(
                    null,
                    "Kost Putra Ciumbuleuit",
                    1600000.0,
                    "Dekat UNPAR.",
                    "KM Dalam, Parkir",
                    List.of("https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600"),
                    -6.877846,
                    107.606272,
                    9,
                    "Admin Dummy",
                    "0800-DUMMY",
                    1L
                ));
                
                System.out.println(">>> 7 Data Kost Dummy (Format List Images) Siap! <<<");
            }
        };
    }
}
