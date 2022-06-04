package com.expensetracker.service;

import com.expensetracker.model.*;
import com.expensetracker.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;


    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }
}
