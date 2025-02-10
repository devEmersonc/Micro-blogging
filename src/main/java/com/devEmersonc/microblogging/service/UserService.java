package com.devEmersonc.microblogging.service;

import com.devEmersonc.microblogging.dto.RegisterUserDTO;
import com.devEmersonc.microblogging.dto.UserDTO;
import com.devEmersonc.microblogging.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUser(Long id);
    void saveNormalUser(RegisterUserDTO registerUserDTO);
    void saveAdminUser(RegisterUserDTO registerUserDTO);
    void updateUser(Long id, RegisterUserDTO registerUserDTO);
    void deleteUser(Long id);
    UserDTO convertEntityToDto(User user);
}
