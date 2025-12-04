package com.example.kost_management.config;

import com.example.kost_management.model.Kost;
import com.example.kost_management.model.User;
import com.example.kost_management.repository.KostRepository;
import com.example.kost_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(KostRepository kostRepo, UserRepository userRepo) {
        return args -> {
            // 1. Reset Data
            kostRepo.deleteAll();
            userRepo.deleteAll();

            // 2. Buat Akun Dummy
            userRepo.save(new User(null, "Pemilik Kost (Admin)", "admin@kost.com", "admin123", "ADMIN"));
            userRepo.save(new User(null, "Anak Kost (User)", "user@kost.com", "user123", "USER"));

            // 3. Buat 10+ Data Kost Dummy
            kostRepo.save(new Kost(null, "Kost Dago Asri", 2500000.0, "Kost eksklusif dekat ITB & Unpad, view bagus, udara sejuk.", "AC, WiFi, KM Dalam, Parkir Mobil", "https://images.unsplash.com/photo-1522771753035-4850d355d242?w=600", -6.884784, 107.613580));
            kostRepo.save(new Kost(null, "Wisma Dipatiukur", 1500000.0, "5 Menit jalan kaki ke UNPAD DU. Banyak makanan murah.", "Kasur, Lemari, WiFi, Dapur Umum", "https://images.unsplash.com/photo-1596296377289-49d7f474024b?w=600", -6.892463, 107.617637));
            kostRepo.save(new Kost(null, "Purple House Pasteur", 1850000.0, "Dekat Maranatha & Tol Pasteur. Bangunan baru.", "AC, TV, Security 24 Jam", "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?w=600", -6.888365, 107.575496));
            kostRepo.save(new Kost(null, "Pondok Ceria Sukabirus", 950000.0, "Murah meriah untuk mahasiswa Telkom University.", "Kasur Busa, KM Luar, WiFi", "https://images.unsplash.com/photo-1621293954908-907159247fc8?w=600", -6.974421, 107.630489));
            kostRepo.save(new Kost(null, "Kost Braga City", 3500000.0, "Apartemen kost di pusat kota Bandung.", "Full Furnished, Kolam Renang, Gym", "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=600", -6.917464, 107.609536));
            
            // Tambahan Data Baru
            kostRepo.save(new Kost(null, "Kost Putri Gegerkalong", 1100000.0, "Khusus putri, dekat UPI & Daarut Tauhid. Lingkungan religius.", "WiFi, Mesin Cuci, Dapur", "https://images.unsplash.com/photo-1598928506311-c55ded91a20c?w=600", -6.863775, 107.594738));
            kostRepo.save(new Kost(null, "Kost Putra Ciumbuleuit", 1600000.0, "Dekat UNPAR, akses mudah ke Cihampelas.", "KM Dalam, Water Heater, Parkir Motor", "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600", -6.877846, 107.606272));
            kostRepo.save(new Kost(null, "Kost Karyawan Buah Batu", 2200000.0, "Cocok untuk karyawan, dekat pintu tol Buah Batu.", "AC, Springbed, TV, Lemari", "https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=600", -6.944883, 107.634863));
            kostRepo.save(new Kost(null, "Paviliun Tubagus Ismail", 2000000.0, "Serasa rumah sendiri, ada ruang tamu mini.", "Ruang Tamu, Dapur Pribadi", "https://images.unsplash.com/photo-1616486338812-3dadae4b4f9d?w=600", -6.886562, 107.622589));
            kostRepo.save(new Kost(null, "Kost Murah Antapani", 850000.0, "Rumahan, bersih, dan aman. Jauh dari kebisingan.", "Kasur, Meja Belajar, KM Luar", "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=600", -6.912629, 107.658252));

            System.out.println(">>> DATA USER & 10 KOST DUMMY SIAP! <<<");
        };
    }
}