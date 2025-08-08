// src/main/java/com/siyamuddin/blog/blogappapis/Security/AuthorizationService.java
package com.siyamuddin.blog.blogappapis.Services;

import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Entity.Comment;
import com.siyamuddin.blog.blogappapis.Entity.User;
import com.siyamuddin.blog.blogappapis.Repository.PostRepo;
import com.siyamuddin.blog.blogappapis.Repository.CommentRepo;
import com.siyamuddin.blog.blogappapis.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("authz")
public class AuthorizationService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    // Check if user can modify/delete a post
    public boolean canModifyPost(Authentication authentication, Integer postId) {
        if (authentication == null) return false;

        String currentEmail = authentication.getName();
        User currentUser = userRepo.findByEmail(currentEmail).orElse(null);
        if (currentUser == null) return false;

        // Check if user is admin
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        // Check if user is owner
        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) return false;

        boolean isOwner = post.getUser().getEmail().equals(currentEmail);

        return isAdmin || isOwner;
    }

    // Check if user can modify/delete a comment
    public boolean canModifyComment(Authentication authentication, Integer commentId) {
        if (authentication == null) return false;

        String currentEmail = authentication.getName();
        User currentUser = userRepo.findByEmail(currentEmail).orElse(null);
        if (currentUser == null) return false;

        // Check if user is admin
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        // Check if user is owner
        Comment comment = commentRepo.findById(commentId).orElse(null);
        if (comment == null) return false;

        boolean isOwner = comment.getUser().getEmail().equals(currentEmail);

        return isAdmin || isOwner;
    }

    // Check if user can modify/delete another user profile
    public boolean canModifyUser(Authentication authentication, Integer userId) {
        if (authentication == null) return false;

        String currentEmail = authentication.getName();
        User currentUser = userRepo.findByEmail(currentEmail).orElse(null);
        if (currentUser == null) return false;

        // Check if user is admin
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        // Check if user is modifying their own profile
        boolean isSelf = currentUser.getId() == userId;

        return isAdmin || isSelf;
    }

    // Check if user can create posts in a category (admin only for category management)
    public boolean canManageCategories(Authentication authentication) {
        if (authentication == null) return false;

        String currentEmail = authentication.getName();
        User currentUser = userRepo.findByEmail(currentEmail).orElse(null);
        if (currentUser == null) return false;

        return currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }
}