package com.siyamuddin.blog.blogappapis.Services;

import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Payloads.PostDto;
import com.siyamuddin.blog.blogappapis.Payloads.PostResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDirec);
    PostDto getPost(Integer postId);
    PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize,String sortBy,String sortDirec);
    PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize,String sortBy, String sortDirec);
    List<PostDto> searchPost(String keyword);
}
