package com.expensetracker.service.impl;

import com.expensetracker.model.Category;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.service.CategoryService;
import com.expensetracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final ExpenseService expenseService;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createNewCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category modifyCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long categoryId) {
        if(!expenseService.expenseWithCategoryExist(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            //TODO throw clientError
        }
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
