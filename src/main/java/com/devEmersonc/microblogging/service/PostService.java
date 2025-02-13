package com.devEmersonc.microblogging.service;

import com.devEmersonc.microblogging.dto.CreatePostDTO;
import com.devEmersonc.microblogging.dto.PostDTO;
import com.devEmersonc.microblogging.dto.UserPostDTO;
import com.devEmersonc.microblogging.model.Post;
import com.devEmersonc.microblogging.model.User;

import java.util.List;

public interface PostService {
    List<PostDTO> getPosts();
    PostDTO getPost(Long id);
    void savePost(CreatePostDTO postDTO);
    void updatePost(Long id, CreatePostDTO postDTO);
    void deletePost(Long id);
    PostDTO convertEntityToDto(Post post);
}
