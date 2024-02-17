package com.example.vortex_games.service;


import com.example.vortex_games.entity.Producto;
import com.example.vortex_games.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //Manual Methods

    public Producto addProduct(Producto producto){
        return productRepository.save(producto);
    }

    public Optional<Producto> searchByName(String name){
        return productRepository.findByName(name);
    }
    public List<Producto> listProducts(){
        return productRepository.findAll();
    }
}
