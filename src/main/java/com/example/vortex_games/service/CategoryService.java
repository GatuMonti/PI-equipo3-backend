package com.example.vortex_games.service;

import com.example.vortex_games.entity.Category;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    //Manual Methods

    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    public Optional<Category> searchByName(String title){
        return categoryRepository.findByTitle(title);
    }

}
