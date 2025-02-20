package com.devEmersonc.microblogging.controller;

import com.devEmersonc.microblogging.dto.CommentDTO;
import com.devEmersonc.microblogging.dto.CreateCommentDTO;
import com.devEmersonc.microblogging.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments() {
        List<CommentDTO> comments = commentService.getComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
        CommentDTO commentDTO = commentService.getComment(id);
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping("/{post_id}")
    public ResponseEntity<String> saveComment(@Valid @RequestBody CreateCommentDTO commentDTO, @PathVariable Long post_id) {
        commentService.saveComment(post_id, commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comentario publicado.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@Valid @RequestBody CreateCommentDTO commentDTO, @PathVariable Long id) {
        commentService.updateComment(id,commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comentario actualizado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comentario eliminado.");
    }
}
