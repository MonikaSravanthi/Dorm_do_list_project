package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.DTO.UserDTO;
import com.taskmanager.taskmanager.entity.Users;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDTO> findALlUsers();
    Users findUserByUsername(String username);
    Users findById(int id);
    void save(Users user);
    Users updateUser(Users user);
    void deleteUser(int id);
    void saveUserImage(Integer userId,MultipartFile file) throws IOException;
  }
