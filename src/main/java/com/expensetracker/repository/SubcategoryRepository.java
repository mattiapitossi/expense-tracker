package com.expensetracker.repository;

import com.expensetracker.model.Category;
import com.expensetracker.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Subcategory findByNameAndCategory(String name, Category category);

    List<Subcategory> findByCategory_Name(String categoryName);
}
