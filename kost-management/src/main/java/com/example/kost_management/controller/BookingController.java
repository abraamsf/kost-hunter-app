package com.example.kost_management.controller;

import com.example.kost_management.model.Booking;
import com.example.kost_management.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            System.out.println("Menerima Booking: " + booking.getCustomerName()); // Cek di terminal
            booking.setStatus("LUNAS");
            Booking savedBooking = bookingRepository.save(booking);
            return ResponseEntity.ok(savedBooking);
        } catch (Exception e) {
            e.printStackTrace(); // Tampilkan error di terminal
            return ResponseEntity.badRequest().body("Gagal simpan: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}