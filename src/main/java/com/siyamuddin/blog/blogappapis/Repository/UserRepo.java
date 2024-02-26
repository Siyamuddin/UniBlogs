package com.siyamuddin.blog.blogappapis.Repository;

import com.siyamuddin.blog.blogappapis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
}
