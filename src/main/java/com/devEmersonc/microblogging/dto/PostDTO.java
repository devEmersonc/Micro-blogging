package com.devEmersonc.microblogging.dto;

import com.devEmersonc.microblogging.model.User;

import java.util.Date;

public class PostDTO {
    private String content;
    private Date createdAt;
    private UserPostDTO author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserPostDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserPostDTO author) {
        this.author = author;
    }
}
