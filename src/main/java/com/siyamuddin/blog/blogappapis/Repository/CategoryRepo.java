package com.siyamuddin.blog.blogappapis.Repository;

import com.siyamuddin.blog.blogappapis.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
