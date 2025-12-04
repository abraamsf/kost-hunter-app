package com.example.kost_management.repository;

import com.example.kost_management.model.Kost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KostRepository extends JpaRepository<Kost, Long> {
    // Mencari data jika Nama ATAU Deskripsi mengandung kata kunci (tidak peduli huruf besar/kecil)
    List<Kost> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}