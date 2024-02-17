package com.example.vortex_games.controller;


import com.example.vortex_games.entity.Producto;
import com.example.vortex_games.exception.BadRequestException;
import com.example.vortex_games.exception.ExistingProductException;
import com.example.vortex_games.service.ImageService;
import com.example.vortex_games.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<Producto> add(@RequestBody Producto producto) throws ExistingProductException {
        Optional<Producto> searchedProduct=productService.searchByName(producto.getName());
        if(searchedProduct.isPresent()){
            throw new ExistingProductException("The name is already in use");
        }
        else if(producto !=null){
            return ResponseEntity.ok(productService.addProduct(producto));
        }
            return ResponseEntity.badRequest().build();

    }

    @GetMapping("/list-products")
    public ResponseEntity<List<Producto>> getProducts(){
        return ResponseEntity.ok(productService.listProducts());
    }


}
