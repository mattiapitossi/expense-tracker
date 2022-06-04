package com.expensetracker.controller;

import com.expensetracker.model.Subcategory;
import com.expensetracker.service.SubcategoryService;
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
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    @GetMapping("/subcategory")
    List<Subcategory> readSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

//    @GetMapping("/subcategory/{id}")
//    ResponseEntity<?> readSubcategoryById(@PathVariable(name = "id") Integer subcategoryId) {
//        return categoryService
//                .findCategoryById(subcategoryId)
//                .map(response -> ResponseEntity.ok().body(response))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

//    @PostMapping("/subcategory")
//    ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) throws URISyntaxException {
//        System.out.println(category);
//        var res = categoryService.createNewCategory(category);
//        return ResponseEntity.created(new URI("/api/category" + res.getId())).body(res);
//    }

//    @PutMapping("/subcategory")
//    ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
//        var res = categoryService.modifyCategory(category);
//        return ResponseEntity.ok().body(res);
//    }
//
//    @DeleteMapping("/subcategory/{id}")
//    ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Integer subcategoryId) {
//        System.out.println("subcategoryId: " + subcategoryId);
//        categoryService.deleteById(subcategoryId);
//        return ResponseEntity.ok().build();
//    }
}
