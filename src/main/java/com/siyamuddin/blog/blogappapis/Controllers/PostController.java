package com.siyamuddin.blog.blogappapis.Controllers;

import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Payloads.ApiResponse;
import com.siyamuddin.blog.blogappapis.Payloads.PostDto;
import com.siyamuddin.blog.blogappapis.Payloads.PostResponse;
import com.siyamuddin.blog.blogappapis.Services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost( @RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId)
    {
        PostDto createPostDto=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPostDto, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,@RequestParam(value = "pageNumber",defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize)
    {
        PostResponse postResponse=this.postService.getPostByUser(userId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
    @RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize
    )
    {
        PostResponse postResponse=this.postService.getPostByCategory(categoryId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId)
    {
        PostDto postDto=this.postService.getPost(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize)
    {
        PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId)
    {
        this.postService.deletePost(postId);
        return new ApiResponse("This post has been deleted.",true);
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
    {
        PostDto postDots=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(postDots,HttpStatus.OK);
    }

}
