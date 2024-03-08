package com.siyamuddin.blog.blogappapis.Payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private Integer commentId;
    private String content;
//    private PostDto post;
    private UserDto user;
}
