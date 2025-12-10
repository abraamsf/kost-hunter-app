package com.example.kost_management.controller;

import com.example.kost_management.model.Kost;
import com.example.kost_management.repository.KostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Wajib import ini

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kosts")
@CrossOrigin(origins = "*") 
public class KostController {

    @Autowired
    private KostRepository kostRepository;
    private final Path rootLocation = Paths.get("uploads"); // Lokasi simpan file

    public KostController() {
        try { Files.createDirectories(rootLocation); } catch (IOException e) {}
    }

    // --- 1. SIMPAN KOST BARU (TERMASUK FILE) ---
    @PostMapping
    public Kost createKost(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("facilities") String facilities,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("ownerName") String ownerName,
            @RequestParam("ownerPhone") String ownerPhone,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("availableRooms") Integer availableRooms,
            @RequestParam(value = "urlImages", required = false) String urlImages, // Input URL manual
            @RequestParam(value = "fileImages", required = false) MultipartFile[] files // Input File
    ) throws IOException {
        
        Kost kost = new Kost();
        kost.setName(name); kost.setPrice(price); kost.setDescription(description);
        kost.setFacilities(facilities); kost.setLatitude(latitude); kost.setLongitude(longitude);
        kost.setOwnerName(ownerName); kost.setOwnerPhone(ownerPhone); kost.setOwnerId(ownerId);
        kost.setAvailableRooms(availableRooms);

        List<String> imageList = new ArrayList<>();

        // 1. Proses Gambar URL (jika ada)
        if (urlImages != null && !urlImages.isEmpty()) {
            imageList.add(urlImages); 
        }

        // 2. Proses Upload File
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    // Simpan path agar bisa diakses lewat browser
                    imageList.add("/content/" + fileName);
                }
            }
        }
        kost.setImages(imageList);
        return kostRepository.save(kost);
    }

    // --- 2. UPDATE / EDIT KOST ---
    @PutMapping("/{id}")
    public Kost updateKost(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("facilities") String facilities,
            @RequestParam(value = "availableRooms", required = false) Integer availableRooms,
            @RequestParam(value = "urlImages", required = false) String urlImages,
            @RequestParam(value = "fileImages", required = false) MultipartFile[] files
    ) throws IOException {
        
        Kost kost = kostRepository.findById(id).orElseThrow(() -> new RuntimeException("Kost not found"));
        
        kost.setName(name);
        kost.setPrice(price);
        kost.setDescription(description);
        kost.setFacilities(facilities);
        if (availableRooms != null) { kost.setAvailableRooms(availableRooms); }

        // Jika ada foto baru (URL), tambahkan
        if (urlImages != null && !urlImages.isEmpty()) {
            kost.getImages().add(urlImages);
        }

        // Jika ada file baru, upload dan tambahkan
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    kost.getImages().add("/content/" + fileName);
                }
            }
        }

        return kostRepository.save(kost);
    }

    @GetMapping
    public List<Kost> getAllKosts(@RequestParam(required = false) String keyword, @RequestParam(required = false) Long ownerId) {
        List<Kost> results = kostRepository.findAll();
        if (ownerId != null) return results.stream().filter(k -> k.getOwnerId() != null && k.getOwnerId().equals(ownerId)).collect(Collectors.toList());
        if (keyword != null && !keyword.isEmpty()) return kostRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        return results;
    }

    @GetMapping("/{id}")
    public Kost getKostById(@PathVariable Long id) { return kostRepository.findById(id).orElseThrow(() -> new RuntimeException("Kost tidak ditemukan")); }

    @DeleteMapping("/{id}")
    public void deleteKost(@PathVariable Long id) { kostRepository.deleteById(id); }
}
