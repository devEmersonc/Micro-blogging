package com.devEmersonc.microblogging.dto;

import java.util.Date;

public class CommentsPostsDTO {
    private Long comment_id;
    private String comment;
    private String author_comment;
    private Date createdAt;

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor_comment() {
        return author_comment;
    }

    public void setAuthor_comment(String author_comment) {
        this.author_comment = author_comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
