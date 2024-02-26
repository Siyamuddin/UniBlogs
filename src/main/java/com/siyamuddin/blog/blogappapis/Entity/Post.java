package com.siyamuddin.blog.blogappapis.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
}
