package com.pbednarz.nonameproject.expense.controllerRest;

import com.pbednarz.nonameproject.expense.model.Category;
import com.pbednarz.nonameproject.expense.repository.CategoryRepository;
import com.pbednarz.nonameproject.expense.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${com.pbednarz.apiPath}/category")
public class CategoryControllerRest {
    // TODO implement methods
    // TODO add authentication
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @Autowired
    public CategoryControllerRest(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    // TODO refactor to respond with default categories + user categories
    @GetMapping
    public ResponseEntity getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getCategory(@PathVariable int id) {
        Category category = categoryRepository.findById((long)id).orElse(null);
        if (category == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            return new ResponseEntity("{\"error\":\"category with provided id not found\"}", headers, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(category);
        }
    }

    @PostMapping
    public ResponseEntity createCategory(@RequestBody @Valid Category category, BindingResult bindingResult) {
        String error = categoryService.checkForErrors(bindingResult);
        if (!error.isEmpty()) {
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        } else {
            categoryRepository.save(category);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }
}
