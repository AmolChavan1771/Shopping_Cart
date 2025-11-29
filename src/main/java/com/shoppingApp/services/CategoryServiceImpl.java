package com.shoppingApp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingApp.model.Category;
import com.shoppingApp.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{


    private CategoryRepository categoryRepository;
    
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
       return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
       return categoryRepository.findAll();
    }

    @Override
    public Boolean existsCategory(String name) {
     
       return categoryRepository.existsByName(name);
    }
    

}
