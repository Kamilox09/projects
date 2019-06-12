package com.java.projects.service;

import com.java.projects.model.Category;
import com.java.projects.repository.CategoryRepository;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category getOrCreateCategory(Category category){
        return categoryRepository.findCategoryByNameIgnoreCase(category.getName())
                .orElseGet(() -> categoryRepository.save(category));
    }
}
