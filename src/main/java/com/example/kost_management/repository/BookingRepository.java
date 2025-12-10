package com.example.kost_management.repository;

import com.example.kost_management.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    // Ambil Booking yang Kost-nya milik Owner tertentu
    @Query("SELECT b FROM Booking b, Kost k WHERE b.kostId = k.id AND k.ownerId = :ownerId")
    List<Booking> findBookingsByOwnerId(Long ownerId);

    List<Booking> findByCustomerEmailAndKostId(String customerEmail, Long kostId);
}
