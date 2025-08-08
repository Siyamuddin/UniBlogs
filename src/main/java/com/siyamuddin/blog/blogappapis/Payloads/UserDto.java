package com.siyamuddin.blog.blogappapis.Payloads;

import com.siyamuddin.blog.blogappapis.Entity.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @NotEmpty
    @Size(min=3,message = "Please give a valid name which contains atleast 3 characters.")
    private String name;
    @Email(message="Your email is not valid.")
    private String email;
    @NotEmpty
    @Size(min=8,max=16,message = "Your password must contain atleast 8 characters and less than 16 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;
    @NotEmpty
    private String about;
}
