package com.devEmersonc.microblogging.service.commentServiceImpl;

import com.devEmersonc.microblogging.dto.CommentDTO;
import com.devEmersonc.microblogging.dto.CreateCommentDTO;
import com.devEmersonc.microblogging.exception.ResourceNotFoundException;
import com.devEmersonc.microblogging.model.Comment;
import com.devEmersonc.microblogging.model.Post;
import com.devEmersonc.microblogging.repository.CommentRepository;
import com.devEmersonc.microblogging.repository.PostRepository;
import com.devEmersonc.microblogging.service.CommentService;
import com.devEmersonc.microblogging.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private SecurityService securityService;

    private CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, SecurityService securityService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.securityService = securityService;
    }

    @Override
    public List<CommentDTO> getComments() {
        return commentRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El comentario ingresado no se ha encontrado."));
        return this.convertEntityToDto(comment);
    }

    @Override
    public void saveComment(Long post_id, CreateCommentDTO commentDTO) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("No se logró encontrar el post."));
        Comment comment = new Comment();

        comment.setComment(commentDTO.getComment());
        comment.setPost(post);
        comment.setAuthor(securityService.getAuthenticateUser());

        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Long id, CreateCommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El comentario ingresado no se ha encontrado."));
        securityService.validateUserPermission(comment.getAuthor());
        comment.setComment(commentDTO.getComment());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El comentario ingresado no se ha encontrado."));
        securityService.validateUserPermission(comment.getAuthor());
        commentRepository.deleteById(comment.getId());
    }

    @Override
    public CommentDTO convertEntityToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setPost_id(comment.getPost().getId());
        commentDTO.setComment_id(comment.getId());
        commentDTO.setAuthor_comment(comment.getAuthor().getUsername());
        commentDTO.setComment(comment.getComment());
        commentDTO.setCreatedAt(comment.getCreatedAt());

        return commentDTO;
    }
}
