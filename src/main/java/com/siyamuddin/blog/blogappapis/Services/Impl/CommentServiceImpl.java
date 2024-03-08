package com.siyamuddin.blog.blogappapis.Services.Impl;

import com.siyamuddin.blog.blogappapis.Entity.Comment;
import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Entity.User;
import com.siyamuddin.blog.blogappapis.Exceptions.ResourceNotFoundException;
import com.siyamuddin.blog.blogappapis.Payloads.CommentDto;
import com.siyamuddin.blog.blogappapis.Repository.CommentRepo;
import com.siyamuddin.blog.blogappapis.Repository.PostRepo;
import com.siyamuddin.blog.blogappapis.Repository.UserRepo;
import com.siyamuddin.blog.blogappapis.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto,Integer userId, Integer postId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Post id",postId));
        Comment comment=this.modelMapper.map(commentDto,Comment.class);
        comment.setUser(user);
        comment.setPost(post);
        Comment addedComment=this.commentRepo.save(comment);
        return this.modelMapper.map(addedComment,CommentDto.class);
    }

    @Override
    public CommentDto updateComment(Integer commentId, CommentDto commentDto) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment Id",commentId));
        comment.setContent(commentDto.getContent());
        Comment updatedComment=this.commentRepo.save(comment);
        return this.modelMapper.map(updatedComment,CommentDto.class);
    }

    @Override
    public CommentDto getComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment Id",commentId));

        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","COmment Id",commentId));
        this.commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments=this.commentRepo.findAll();
        List<CommentDto>commentDtos=comments.stream().map((comment)->this.modelMapper.map(comment,CommentDto.class)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public List<CommentDto> getCommentByPost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Post id",postId));
        List<Comment> comments=this.commentRepo.findByPost(post);
        List<CommentDto> commentDtos=comments.stream().map((comment)->this.modelMapper.map(comment,CommentDto.class)).collect(Collectors.toList());

        return commentDtos;
    }

    @Override
    public List<CommentDto> getCommentByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
        List<Comment> comments=this.commentRepo.findByUser(user);
        List<CommentDto> commentDtos=comments.stream().map((comment)->this.modelMapper.map(comment,CommentDto.class)).collect(Collectors.toList());

        return commentDtos;
    }
}
