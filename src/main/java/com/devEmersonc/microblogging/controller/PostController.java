package com.devEmersonc.microblogging.controller;

import com.devEmersonc.microblogging.dto.CreatePostDTO;
import com.devEmersonc.microblogging.dto.PostDTO;
import com.devEmersonc.microblogging.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts() {
        List<PostDTO> posts = postService.getPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        PostDTO post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<String> savePost(@Valid @RequestBody CreatePostDTO postDTO) {
        postService.savePost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post creado con éxito.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@Valid @RequestBody CreatePostDTO postDTO, @PathVariable Long id) {
        postService.updatePost(id, postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post actualizado con éxito.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post eliminado.");
    }
}
