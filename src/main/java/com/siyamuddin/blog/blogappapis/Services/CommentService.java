package com.siyamuddin.blog.blogappapis.Services;

import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Payloads.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer userId,Integer postId);
    CommentDto updateComment(Integer commentId, CommentDto commentDto);
    CommentDto getComment(Integer commentId);
    void deleteComment(Integer commentId);
    List<CommentDto> getAllComments();
    List<CommentDto> getCommentByPost(Integer postId);
    List<CommentDto> getCommentByUser(Integer userId);

}
