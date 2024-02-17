package com.example.vortex_games.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String imageUrl;

    @NonNull
    @ManyToOne
    @JoinColumn(name="id_product",referencedColumnName = "id")
    private Producto product;






}
