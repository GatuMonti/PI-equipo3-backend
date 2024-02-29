package com.example.vortex_games.repository;

import com.example.vortex_games.entity.Category;
import com.example.vortex_games.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitle(String name);
}
