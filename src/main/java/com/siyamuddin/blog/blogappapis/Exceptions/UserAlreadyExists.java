package com.siyamuddin.blog.blogappapis.Exceptions;

public class UserAlreadyExists extends RuntimeException {

    private String username;
    private String id;
    public UserAlreadyExists(String username, String id) {
        super(String.format("User "+username+" with user Email:"+id+" is alreeady exist with this email."));
        this.username=username;
        this.id= id;
    }
}
