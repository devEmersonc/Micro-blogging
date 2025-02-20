package com.devEmersonc.microblogging.service.userServiceImpl;

import com.devEmersonc.microblogging.dto.RegisterUserDTO;
import com.devEmersonc.microblogging.dto.UserPostsDTO;
import com.devEmersonc.microblogging.exception.AccessDeniedException;
import com.devEmersonc.microblogging.exception.ResourceNotFoundException;
import com.devEmersonc.microblogging.model.User;
import com.devEmersonc.microblogging.repository.RoleRepository;
import com.devEmersonc.microblogging.repository.UserRepository;
import com.devEmersonc.microblogging.service.SecurityService;
import com.devEmersonc.microblogging.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, SecurityService securityService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
    }

    @Override
    public List<UserPostsDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserPostsDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No fue posible encontrar al usuario ingresado."));
        return this.convertEntityToDto(user);
    }

    @Override
    public void saveNormalUser(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }

    @Override
    public void saveAdminUser(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, RegisterUserDTO registerUserDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No fue posible encontrar al usuario ingresado."));
        securityService.validateUserPermission(user);
        user.setUsername(registerUserDTO.getUsername());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No fue posible encontrar al usuario ingresado."));
        if(!securityService.isAdmin()) {
            throw new AccessDeniedException("Acceso denegado: No tienes permisos.");
        }
        userRepository.deleteById(user.getId());
    }

    @Override
    public UserPostsDTO convertEntityToDto(User user) {
        UserPostsDTO userDTO = new UserPostsDTO();

        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPosts(user.getPosts());
        return userDTO;
    }
}