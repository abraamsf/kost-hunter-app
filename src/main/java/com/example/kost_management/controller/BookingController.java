package com.example.kost_management.controller;

import com.example.kost_management.model.Booking;
import com.example.kost_management.model.Kost;
import com.example.kost_management.repository.BookingRepository;
import com.example.kost_management.repository.KostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private KostRepository kostRepository;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Kost kost = kostRepository.findById(booking.getKostId()).orElse(null);
            if (kost == null) return ResponseEntity.badRequest().body("Kost tidak ditemukan");
            Integer avail = kost.getAvailableRooms();
            if (avail == null || avail <= 0) return ResponseEntity.badRequest().body("Kamar penuh / tidak tersedia");

            List<Booking> existing = bookingRepository.findByCustomerEmailAndKostId(booking.getCustomerEmail(), booking.getKostId());
            LocalDate today = LocalDate.now();
            for (Booking b : existing) {
                if (b.getStartDate() != null && b.getDurationInMonths() != null) {
                    LocalDate endDate = b.getStartDate().plusMonths(b.getDurationInMonths());
                    if (!endDate.isBefore(today)) {
                        return ResponseEntity.badRequest().body("Anda sudah memiliki booking aktif untuk kost ini");
                    }
                }
            }

            kost.setAvailableRooms(avail - 1);
            kostRepository.save(kost);

            booking.setStatus("LUNAS");
            Booking savedBooking = bookingRepository.save(booking);
            return ResponseEntity.ok(savedBooking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Gagal simpan: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Booking> getAllBookings(@RequestParam(required = false) Long ownerId) {
        if (ownerId != null) {
            // Hanya ambil booking milik owner yang login
            return bookingRepository.findBookingsByOwnerId(ownerId);
        }
        return bookingRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
    }
}
