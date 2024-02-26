package com.siyamuddin.blog.blogappapis.Controllers;

import com.siyamuddin.blog.blogappapis.Entity.Category;
import com.siyamuddin.blog.blogappapis.Payloads.ApiResponse;
import com.siyamuddin.blog.blogappapis.Payloads.CategoryDto;
import com.siyamuddin.blog.blogappapis.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createCategory=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id)
    {
        CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto,id);
        return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id)
    {
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("The category has been deleted",true),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id)
    {
        CategoryDto categoryDto=this.categoryService.getCategory(id);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories()
    {
        List<CategoryDto> categoryDtos=this.categoryService.getCategories();
        return ResponseEntity.ok(categoryDtos);
    }

}
