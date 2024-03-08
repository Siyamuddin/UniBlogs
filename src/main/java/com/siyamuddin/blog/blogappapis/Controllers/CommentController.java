package com.siyamuddin.blog.blogappapis.Controllers;

import com.siyamuddin.blog.blogappapis.Entity.User;
import com.siyamuddin.blog.blogappapis.Payloads.ApiResponse;
import com.siyamuddin.blog.blogappapis.Payloads.CommentDto;
import com.siyamuddin.blog.blogappapis.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/add/user/{userId}/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("userId") Integer userId, @PathVariable("userId") Integer postId)
    {
        CommentDto commentDto1=this.commentService.createComment(commentDto,userId,postId);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }
    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId)
    {
        CommentDto commentDto1=this.commentService.updateComment(commentId,commentDto);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }
    @GetMapping("/get/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Integer commentId)
    {
        CommentDto commentDto=this.commentService.getComment(commentId);
        return new ResponseEntity<CommentDto>(commentDto,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId)
    {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("this comment has been deleted.",true),HttpStatus.OK);
    }
    @GetMapping("/get/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPost(@PathVariable Integer postId)
    {
        List<CommentDto> commentDtos=this.commentService.getCommentByPost(postId);
        return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
    }
    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentByUser(@PathVariable Integer userId)
    {
        List<CommentDto>commentDtos=this.commentService.getCommentByUser(userId);
        return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
    }
    @GetMapping("/get/all")
    public ResponseEntity<List<CommentDto>> getAllComments()
    {
        List<CommentDto> commentDtos=this.commentService.getAllComments();
        return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
    }


}
