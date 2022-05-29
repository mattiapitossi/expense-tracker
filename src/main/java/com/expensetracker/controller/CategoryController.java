package com.expensetracker.controller;

import com.expensetracker.model.Category;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.service.CategoryService;
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
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @GetMapping("/category")
    List<Category> readCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    ResponseEntity<?> readCategoryById(@PathVariable Integer id) {
        return categoryRepository
                .findById(id)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/category")
    ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) throws URISyntaxException {
        var res = categoryRepository.save(category);
        return ResponseEntity.created(new URI("/api/category" + res.getId())).body(res);
    }

    @PutMapping("/category")
    ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
        var res = categoryRepository.save(category);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/category")
    ResponseEntity<?> deleteCategory(@PathVariable Category category) {
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }
}
