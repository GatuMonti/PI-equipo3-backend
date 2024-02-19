package com.example.vortex_games.service;


import com.example.vortex_games.entity.Product;
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

    public Product addProduct(Product producto){
        System.out.println(producto);
        producto.getImages().forEach(image -> image.setProduct(producto));
        return productRepository.save(producto);
    }
    public List<Product> searchByCategory(String category){
        return productRepository.findByCategory(category);
    }

    public Optional<Product> searchById(Long id){
        return productRepository.findById(id);
    }
    public Optional<Product> searchByName(String name){
        return productRepository.findByName(name);
    }
    public List<Product> listProducts(){
        return productRepository.findAll();
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

}