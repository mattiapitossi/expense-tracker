package com.expensetracker.service;

import com.expensetracker.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();

    Optional<Category> findCategoryById(Integer id);

    Category createNewCategory(Category category);

    Category modifyCategory(Category category);

    void deleteById(Integer categoryId);

    Category findByName(String name);
}
