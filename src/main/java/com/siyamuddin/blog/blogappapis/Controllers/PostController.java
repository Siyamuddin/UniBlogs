package com.siyamuddin.blog.blogappapis.Controllers;

import com.siyamuddin.blog.blogappapis.Config.AppConstants;
import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Payloads.ApiResponse;
import com.siyamuddin.blog.blogappapis.Payloads.PostDto;
import com.siyamuddin.blog.blogappapis.Payloads.PostResponse;
import com.siyamuddin.blog.blogappapis.Services.FileService;
import com.siyamuddin.blog.blogappapis.Services.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
@SecurityRequirement(name = "JWT-Auth")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost( @RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId)
    {
        PostDto createPostDto=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPostDto, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
                                                      @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                      @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                      @RequestParam(value = "sortDirec", defaultValue = AppConstants.SORT_DIREC,required = false) String sortDirec)
    {
        PostResponse postResponse=this.postService.getPostByUser(userId,pageNumber,pageSize,sortBy,sortDirec);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
                                                          @RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                                          @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                          @RequestParam(value = "sortDirec",defaultValue = AppConstants.SORT_DIREC,required = false) String sortDirec)
    {
        PostResponse postResponse=this.postService.getPostByCategory(categoryId,pageNumber,pageSize,sortBy,sortDirec);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId)
    {
        PostDto postDto=this.postService.getPost(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                   @RequestParam(value = "sortDirec",defaultValue = AppConstants.SORT_DIREC,required = false) String sortDirec)
    {
        PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDirec);
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
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword)
    {
        List<PostDto> dtos=this.postService.searchPost(keyword);
        return new ResponseEntity<List<PostDto>>(dtos,HttpStatus.OK);
    }
    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId) throws IOException {
        PostDto postDto=this.postService.getPost(postId);
    String fileName=this.fileService.uploadImage(path,image);

    postDto.setImageName(fileName);
    PostDto updatedPost=this.postService.updatePost(postDto,postId);
    return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource=this.fileService.getSource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
