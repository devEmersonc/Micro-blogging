package com.devEmersonc.microblogging.service;

import com.devEmersonc.microblogging.dto.CommentDTO;
import com.devEmersonc.microblogging.dto.CreateCommentDTO;
import com.devEmersonc.microblogging.model.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getComments();
    CommentDTO getComment(Long id);
    void saveComment(Long post_id, CreateCommentDTO commentDTO);
    void updateComment(Long id, CreateCommentDTO commentDTO);
    void deleteComment(Long id);
    CommentDTO convertEntityToDto(Comment comment);
}
