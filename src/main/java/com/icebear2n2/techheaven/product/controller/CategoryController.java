package com.icebear2n2.techheaven.product.controller;

import com.icebear2n2.techheaven.domain.request.CategoryIDRequest;
import com.icebear2n2.techheaven.domain.request.CategoryRequest;
import com.icebear2n2.techheaven.domain.response.CategoryResponse;
import com.icebear2n2.techheaven.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        if (categoryResponse.isSuccess()) {
            return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<Page<CategoryResponse.CategoryData>> getAllByCategories(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        PageRequest request = PageRequest.of(page, size);
        if (name != null) {
            return new ResponseEntity<>(categoryService.findByCategoryNameContaining(name, request), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(categoryService.findByCategoryNameContaining(null, request), HttpStatus.OK);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryRequest);
        if (categoryResponse.isSuccess()) {
            return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeCategory(@RequestBody CategoryIDRequest categoryIDRequest) {
        categoryService.removeCategory(categoryIDRequest.getCategoryId());
        return new ResponseEntity<>("Category removed successfully.", HttpStatus.OK);
    }
}