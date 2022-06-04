package com.expensetracker.repository;

import com.expensetracker.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    Subcategory findByName(String name);

}
