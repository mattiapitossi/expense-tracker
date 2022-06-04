package com.expensetracker.service;

import com.expensetracker.model.*;
import com.expensetracker.repository.SubcategoryRepository;
import com.expensetracker.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryServiceImpl categoryService;


    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    public Subcategory createNewSubcategory(Subcategory subcategory) {
        Category category = categoryService.findByName(subcategory.getCategory().getName());
        subcategory.setCategory(category);

        return subcategoryRepository.save(subcategory);
    }
}
