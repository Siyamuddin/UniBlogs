package com.siyamuddin.blog.blogappapis.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtRequest {
    private  String email;
    private String password;
}
