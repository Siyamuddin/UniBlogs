package com.siyamuddin.blog.blogappapis.Controllers;

import com.siyamuddin.blog.blogappapis.Entity.JwtRequest;
import com.siyamuddin.blog.blogappapis.Entity.JwtResponse;
import com.siyamuddin.blog.blogappapis.Payloads.SecurityEventLogger;
import com.siyamuddin.blog.blogappapis.Payloads.UserDto;
import com.siyamuddin.blog.blogappapis.Security.JwtHelper;


import com.siyamuddin.blog.blogappapis.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private SecurityEventLogger securityEventLogger;

    // Update the login method:
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request, HttpServletRequest httpRequest) {
        try {
            this.doAuthenticate(request.getEmail(), request.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = this.helper.generateToken(userDetails);

            // Log successful login
            securityEventLogger.logLoginAttempt(request.getEmail(), getClientIP(httpRequest), true);

            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            // Log failed login attempt
            securityEventLogger.logLoginAttempt(request.getEmail(), getClientIP(httpRequest), false);
            throw e;
        }
    }

    // Add this helper method:
    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    //register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
    {
        UserDto registeredUser=this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
    }
}
