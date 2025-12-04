package com.example.kost_management.controller;

import com.example.kost_management.model.Kost;
import com.example.kost_management.repository.KostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kosts")
@CrossOrigin(origins = "*") 
public class KostController {

    @Autowired
    private KostRepository kostRepository;

    @GetMapping
    public List<Kost> getAllKosts(@RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            // Jika ada keyword pencarian, cari yang cocok
            return kostRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        } else {
            // Jika kosong, tampilkan semua
            return kostRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    public Kost getKostById(@PathVariable Long id) {
        return kostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kost tidak ditemukan"));
    }
}