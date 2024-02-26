package com.siyamuddin.blog.blogappapis.Payloads;

import com.siyamuddin.blog.blogappapis.Entity.Category;
import com.siyamuddin.blog.blogappapis.Entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    @NotEmpty(message = "Title can not be empty.")
    @Size(min = 2,message = "Title must be more than 2 charecters.")
    private String title;
    @NotEmpty(message = "Content Can not be Empty.")
    @Size(min = 2,message = "Content must be more than 2 charecters.")
    private String content;
    private String imageName;
    private Date addedDate;
    private UserDto user;
    private CategoryDto category;
}
