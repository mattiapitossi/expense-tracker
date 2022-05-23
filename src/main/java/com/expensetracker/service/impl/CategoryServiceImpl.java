package com.expensetracker.service.impl;

import com.expensetracker.model.Category;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
