package com.example.vortex_games.controller;

import com.example.vortex_games.entity.Category;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.exception.ExistingProductException;
import com.example.vortex_games.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-categoria")
    public ResponseEntity<Category> addCategoria(@RequestBody Category category) throws ExistingProductException {
        Optional<Category> searchedCategoria=categoryService.searchByName(category.getTitle());
        if(searchedCategoria.isPresent()){
            throw new ExistingProductException("The title is already in use");
        }
        else if(category !=null){
            return ResponseEntity.ok(categoryService.addCategory(category));
        }
        return ResponseEntity.badRequest().build();

    }
}
