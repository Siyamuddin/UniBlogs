package com.siyamuddin.blog.blogappapis.Services.Impl;

import com.siyamuddin.blog.blogappapis.Entity.Category;
import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Entity.User;
import com.siyamuddin.blog.blogappapis.Exceptions.ResourceNotFoundException;
import com.siyamuddin.blog.blogappapis.Payloads.PostDto;
import com.siyamuddin.blog.blogappapis.Repository.CategoryRepo;
import com.siyamuddin.blog.blogappapis.Repository.PostRepo;
import com.siyamuddin.blog.blogappapis.Repository.UserRepo;
import com.siyamuddin.blog.blogappapis.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));
        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post newPost=this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId, Integer userId,Integer categoryId) {

        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<Post> getAllPost() {
        return null;
    }

    @Override
    public Post getPost(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category Id",categoryId));
        List<Post> posts=this.postRepo.findByCategory(category);
        List<PostDto> postDtoList=posts.stream().map((Post)-> this.modelMapper.map(posts,PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user Id",userId));
        List<Post> posts=this.postRepo.findByUser(user);
        List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(posts,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<Post> searchPost(String keyword) {
        return null;
    }
}
