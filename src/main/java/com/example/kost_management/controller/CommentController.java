package com.example.kost_management.controller;

import com.example.kost_management.model.Comment;
import com.example.kost_management.model.User;
import com.example.kost_management.repository.CommentRepository;
import com.example.kost_management.repository.UserRepository;
import com.example.kost_management.repository.ReviewRepository;
import com.example.kost_management.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public List<Comment> getByKost(@RequestParam Long kostId) {
        return commentRepository.findByKostIdOrderByCreatedAtAsc(kostId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Comment comment) {
        if (comment.getUserId() == null) return ResponseEntity.status(401).body("Harus login sebagai USER");
        Optional<User> u = userRepository.findById(comment.getUserId());
        if (u.isEmpty() || u.get().getRole() == null || !u.get().getRole().equals("USER")) {
            return ResponseEntity.status(403).body("Hanya USER yang boleh berkomentar");
        }
        if (comment.getKostId() == null) {
            return ResponseEntity.badRequest().body("KostId wajib diisi");
        }
        boolean hasBooking = !bookingRepository.findByCustomerEmailAndKostId(u.get().getEmail(), comment.getKostId()).isEmpty();
        if (!hasBooking) {
            return ResponseEntity.status(403).body("Komentar hanya untuk penyewa yang pernah memesan kost ini");
        }
        boolean hasReview = reviewRepository.existsByKostIdAndUserId(comment.getKostId(), comment.getUserId());
        if (!hasReview) {
            return ResponseEntity.badRequest().body("Komentar hanya bisa setelah memberi rating & ulasan");
        }
        if (comment.getParentId() == null) {
            return ResponseEntity.badRequest().body("Komentar utama harus dibuat saat memberi rating (gunakan ulasan)");
        }
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);
        return ResponseEntity.ok(saved);
    }
}
