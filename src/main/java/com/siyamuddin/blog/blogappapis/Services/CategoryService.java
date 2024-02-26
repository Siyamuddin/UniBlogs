package com.siyamuddin.blog.blogappapis.Services;

import com.siyamuddin.blog.blogappapis.Entity.Category;
import com.siyamuddin.blog.blogappapis.Payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService
{
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    public void deleteCategory(Integer id);
    CategoryDto getCategory(Integer categoryId);
    List<CategoryDto> getCategories();

}
