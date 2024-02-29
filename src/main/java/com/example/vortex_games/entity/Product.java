package com.example.vortex_games.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="Products")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;


<<<<<<< HEAD
    @ManyToOne( fetch = FetchType.EAGER)
=======
    @ManyToOne( cascade= CascadeType.MERGE ,fetch = FetchType.EAGER)
>>>>>>> a760f4de5820a62807d96ef3d4610e3785e97a04
    @JoinColumn(name = "category_id")
    private Category category;



    @NonNull
    @Column(nullable = false)
    private String description;

    @NonNull
    @Column(nullable = false)
    private double price;

    @NonNull
    @Column(nullable = false)
    private String type;

    @NonNull
    @Column(nullable = false)
    private String console;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Image> images=new HashSet<>();


    @ManyToMany(cascade =  CascadeType.MERGE)
    @JoinTable(
            name = "producto_caracteristicas",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "characteristic_id")
    )
    private Set<Characteristic> characteristics=new HashSet<>();

}