package com.siyamuddin.blog.blogappapis.Services;

import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Payloads.PostDto;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer postId,Integer userId,Integer categoryId);
    void deletePost(Integer postId);
    List<Post> getAllPost();
    Post getPost(Integer postId);
    List<PostDto> getPostByCategory(Integer categoryId);
    List<PostDto> getPostByUser(Integer userId);
    List<Post> searchPost(String keyword);
}
