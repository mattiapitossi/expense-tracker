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

    @PostMapping("/subcategory")
    ResponseEntity<Subcategory> createSubcategory(@Valid @RequestBody Subcategory subcategory) throws URISyntaxException {
        System.out.println(subcategory);
        Subcategory res = subcategoryService.createNewSubcategory(subcategory);
        return ResponseEntity.created(new URI("/api/subcategory" + res.getId())).body(res);
    }

    @PutMapping("/subcategory")
    ResponseEntity<Subcategory> updateSubcategory(@Valid @RequestBody Subcategory subcategory) {
        var res = subcategoryService.modifySubcategory(subcategory);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/subcategory/{id}")
    ResponseEntity<Subcategory> deleteCategory(@PathVariable(name = "id") Integer subcategoryId) {
        System.out.println("subcategoryId: " + subcategoryId);
        subcategoryService.deleteById(subcategoryId);
        return ResponseEntity.ok().build();
    }
}
