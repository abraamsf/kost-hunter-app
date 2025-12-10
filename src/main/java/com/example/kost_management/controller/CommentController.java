package com.example.kost_management.controller;

import com.example.kost_management.model.Comment;
import com.example.kost_management.model.User;
import com.example.kost_management.repository.CommentRepository;
import com.example.kost_management.repository.UserRepository;
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
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);
        return ResponseEntity.ok(saved);
    }
}
