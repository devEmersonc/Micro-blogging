package com.devEmersonc.microblogging.dto;

import com.devEmersonc.microblogging.model.Post;

import java.util.Date;

public class CommentDTO {
    private Long comment_id;
    private Long post_id;
    private String author_comment;
    private String comment;
    private Date createdAt;

    public CommentDTO(){};

    public CommentDTO(Long comment_id, Long post_id, String author_comment, String comment, Date createdAt) {
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.author_comment = author_comment;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public CommentDTO(Long comment_id, String author_comment, String comment, Date createdAt) {
        this.comment_id = comment_id;
        this.author_comment = author_comment;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getAuthor_comment() {
        return author_comment;
    }

    public void setAuthor_comment(String author_comment) {
        this.author_comment = author_comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
