package com.devEmersonc.microblogging.service.postServiceImpl;

import com.devEmersonc.microblogging.dto.CommentDTO;
import com.devEmersonc.microblogging.dto.CreatePostDTO;
import com.devEmersonc.microblogging.dto.PostDTO;
import com.devEmersonc.microblogging.dto.UserDTO;
import com.devEmersonc.microblogging.exception.AccessDeniedException;
import com.devEmersonc.microblogging.exception.ResourceNotFoundException;
import com.devEmersonc.microblogging.model.Post;
import com.devEmersonc.microblogging.repository.PostRepository;
import com.devEmersonc.microblogging.service.PostService;
import com.devEmersonc.microblogging.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SecurityService securityService;

    public PostServiceImpl(PostRepository postRepository, SecurityService securityService) {
        this.postRepository = postRepository;
        this.securityService = securityService;
    }

    @Override
    public List<PostDTO> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El post ingresado no se ha encontrado."));
        return this.convertEntityToDto(post);
    }

    @Override
    public void savePost(CreatePostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setAuthor(securityService.getAuthenticateUser());
        postRepository.save(post);
    }

    @Override
    public void updatePost(Long id, CreatePostDTO postDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El post ingresado no se ha encontrado."));
        securityService.validateUserPermission(post.getAuthor());
        post.setContent(postDTO.getContent());
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El post ingresado no se ha encontrado."));
        securityService.validateUserPermission(post.getAuthor());
        postRepository.deleteById(post.getId());
    }

    @Override
    public PostDTO convertEntityToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        UserDTO author = new UserDTO();
        List<CommentDTO> commentsDTO = post.getComments().stream()
                        .map(comment -> new CommentDTO(comment.getId(), comment.getAuthor().getUsername(), comment.getComment(), comment.getCreatedAt()))
                                .collect(Collectors.toList());

        author.setId(post.getAuthor().getId());
        author.setUsername(post.getAuthor().getUsername());

        postDTO.setPost_id(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setAuthor(author);
        postDTO.setComments(commentsDTO);

        return postDTO;
    }
}