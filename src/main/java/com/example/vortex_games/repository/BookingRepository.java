package com.example.vortex_games.repository;

import com.example.vortex_games.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking , Long> {

}
