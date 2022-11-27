package com.expensetracker.controller;

import com.expensetracker.model.Category;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.service.CategoryService;
import com.expensetracker.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping("/category")
    ResponseEntity<List<Category>> readCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @GetMapping("/category/{id}")
    ResponseEntity<?> readCategoryById(@PathVariable Integer id) {
        return categoryService
                .findCategoryById(id)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/category")
    ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) throws URISyntaxException {
        var res = categoryService.createNewCategory(category);
        return ResponseEntity.created(new URI("/api/category" + res.getId())).body(res);
    }

    @PutMapping("/category")
    ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
        var res = categoryService.modifyCategory(category);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/category/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
