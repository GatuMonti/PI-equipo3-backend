package com.example.vortex_games.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Entity

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String username;
    private String email;
    private String password;
}
