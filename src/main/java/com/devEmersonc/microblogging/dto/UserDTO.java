package com.devEmersonc.microblogging.dto;

import com.devEmersonc.microblogging.model.Post;

import java.util.List;

public class UserDTO {
    private String username;
    private String email;
    private List<Post> posts;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
