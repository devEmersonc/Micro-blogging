package com.devEmersonc.microblogging.service;

import com.devEmersonc.microblogging.exception.AccessDeniedException;
import com.devEmersonc.microblogging.model.Comment;
import com.devEmersonc.microblogging.model.Post;
import com.devEmersonc.microblogging.model.User;
import com.devEmersonc.microblogging.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new AuthenticationCredentialsNotFoundException("Debes iniciar sesión para realizar esta acción.");
        }

        return userRepository.findByUsername(authentication.getName());
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

    public void validateUserPermission(User resourceOwner) {
        String authenticatedUsername = getAuthenticateUser().getUsername();
        if(!resourceOwner.getUsername().equals(authenticatedUsername) && !isAdmin()) {
            throw new AccessDeniedException("Acceso denegado: No tienes permisos.");
        }
    }
}
