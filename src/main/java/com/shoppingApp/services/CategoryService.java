package com.shoppingApp.services;

import java.util.List;

import com.shoppingApp.model.Category;

public interface CategoryService {

    public Category saveCategory(Category Category);
    
    public List<Category> getAllCategory();

    public Boolean existsCategory(String name);
}
