package com.siyamuddin.blog.blogappapis.Repository;

import com.siyamuddin.blog.blogappapis.Entity.Category;
import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category Category, Pageable pageable);
    List<Post> findByTitleContaining(String title);
}
