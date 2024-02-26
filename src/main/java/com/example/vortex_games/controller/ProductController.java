package com.example.vortex_games.controller;

import com.example.vortex_games.entity.Product;
import com.example.vortex_games.exception.ExistingProductException;
import com.example.vortex_games.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Product> add(@RequestBody Product producto) throws ExistingProductException {
        Optional<Product> searchedProduct=productService.searchByName(producto.getName());
        if(searchedProduct.isPresent()){
            throw new ExistingProductException("The name is already in use");
        }
        else if(producto !=null){
            return ResponseEntity.ok(productService.addProduct(producto));
        }
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/list-products")
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.listProducts());
    }

    @GetMapping("/search-category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category){
        if(productService.searchByCategory(category).size()>0){
            return ResponseEntity.ok(productService.searchByCategory(category));
        }
        else{
            return ResponseEntity.ok(productService.listProducts());
        }
    }

    @GetMapping("/search-id/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.searchById(id));
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<String> deleteProducts(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("The product has been deleted", HttpStatus.OK);
    }

    @PutMapping("/update-product")
    public ResponseEntity<String> updateProduct(@RequestBody Product product){
        productService.updateProduct(product);
        return new ResponseEntity<>("product successfully updated", HttpStatus.OK);
    }

}