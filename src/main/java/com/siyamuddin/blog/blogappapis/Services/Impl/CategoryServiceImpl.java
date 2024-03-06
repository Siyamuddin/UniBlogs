package com.siyamuddin.blog.blogappapis.Services.Impl;

import com.siyamuddin.blog.blogappapis.Entity.Category;
import com.siyamuddin.blog.blogappapis.Exceptions.ResourceNotFoundException;
import com.siyamuddin.blog.blogappapis.Payloads.CategoryDto;
import com.siyamuddin.blog.blogappapis.Repository.CategoryRepo;
import com.siyamuddin.blog.blogappapis.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat=this.modelMapper.map(categoryDto,Category.class);
        Category addedCat=this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category addedCat=this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category cat=this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",id));
        this.categoryRepo.delete(cat);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));


        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories(Integer pagegeNumber, Integer pageSize,String sortBy, String sortDirec) {
        Sort sort=null;
        if(sortDirec.equalsIgnoreCase("asc"))
        {
            sort=Sort.by(sortBy).ascending();
        }
        else
        {
            sort=Sort.by(sortBy).descending();
        }
        Pageable pageable= PageRequest.of(pagegeNumber,pageSize,sort);
        Page<Category> categories=this.categoryRepo.findAll(pageable);

//        List<Category> categories=this.categoryRepo.findAll(sort);
        List<CategoryDto> catDtos=categories.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());

        return catDtos;
    }
}
