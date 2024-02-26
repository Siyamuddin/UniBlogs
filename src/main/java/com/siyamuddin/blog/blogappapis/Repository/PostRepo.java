package com.siyamuddin.blog.blogappapis.Repository;

import com.siyamuddin.blog.blogappapis.Entity.Category;
import com.siyamuddin.blog.blogappapis.Entity.Post;
import com.siyamuddin.blog.blogappapis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category Category);
}
