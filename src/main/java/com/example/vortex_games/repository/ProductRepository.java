package com.example.vortex_games.repository;

import com.example.vortex_games.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Producto,Long> {

    Optional<Producto> findByName(String name);


}
