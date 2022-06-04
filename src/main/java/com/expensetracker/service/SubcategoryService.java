package com.expensetracker.service;

import com.expensetracker.model.*;
import com.expensetracker.repository.SubcategoryRepository;
import com.expensetracker.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

    public Subcategory modifySubcategory(Subcategory subcategory) {
        Category category = categoryService.findByName(subcategory.getCategory().getName());
        subcategory.setCategory(category);

        Subcategory oldSubcategory = subcategoryRepository.findById(subcategory.getId()).get();
        BeanUtils.copyProperties(subcategory, oldSubcategory);

        return subcategoryRepository.save(oldSubcategory);
    }

    public void deleteById(Integer subcategoryId) {
        subcategoryRepository.deleteById(subcategoryId);
    }
}
