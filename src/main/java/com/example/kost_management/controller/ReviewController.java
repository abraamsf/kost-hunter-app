package com.example.kost_management.controller;

import com.example.kost_management.model.Review;
import com.example.kost_management.model.User;
import com.example.kost_management.repository.ReviewRepository;
import com.example.kost_management.repository.UserRepository;
import com.example.kost_management.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public List<Review> getByKost(@RequestParam Long kostId) {
        return reviewRepository.findByKostIdOrderByCreatedAtDesc(kostId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Review review) {
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            return ResponseEntity.badRequest().body("Rating harus 1-5");
        }
        if (review.getText() == null || review.getText().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Ulasan tidak boleh kosong");
        }
        if (review.getUserId() == null) {
            return ResponseEntity.status(401).body("Harus login sebagai USER");
        }
        User u = userRepository.findById(review.getUserId()).orElse(null);
        if (u == null || u.getRole() == null || !u.getRole().equals("USER")) {
            return ResponseEntity.status(403).body("Hanya USER yang boleh memberi ulasan");
        }
        if (review.getKostId() == null) {
            return ResponseEntity.badRequest().body("KostId wajib diisi");
        }
        boolean hasBooking = !bookingRepository.findByCustomerEmailAndKostId(u.getEmail(), review.getKostId()).isEmpty();
        if (!hasBooking) {
            return ResponseEntity.status(403).body("Hanya penyewa yang pernah memesan yang boleh memberi ulasan");
        }
        if (reviewRepository.existsByKostIdAndUserId(review.getKostId(), review.getUserId())) {
            return ResponseEntity.badRequest().body("Kamu sudah pernah memberi rating & ulasan untuk kost ini");
        }
        review.setCreatedAt(LocalDateTime.now());
        Review saved = reviewRepository.save(review);
        return ResponseEntity.ok(saved);
    }
}
