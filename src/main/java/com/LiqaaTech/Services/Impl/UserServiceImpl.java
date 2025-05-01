package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.UserDTO;
import com.LiqaaTech.Entities.User;
import com.LiqaaTech.Enums.Role;
import com.LiqaaTech.Exceptions.NotFoundException;
import com.LiqaaTech.Mappers.UserMapper;
import com.LiqaaTech.Repositories.UserRepository;
import com.LiqaaTech.Services.Interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }

        User user = userMapper.toEntity(userDTO);
        if(user.getRole() == null) {
            user.setRole(Role.PARTICIPANT);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new NotFoundException("No Users found");
        }
        return userMapper.toDTOList(users);
    }

    @Override
    public UserDTO getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        if (id == null || userDTO == null) {
            throw new IllegalArgumentException("User ID and data cannot be null");
        }

        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        savedUser.setEmail(userDTO.getEmail());
        savedUser.setUsername(userDTO.getUsername());
        savedUser.setRole(userDTO.getRole());
        savedUser.setEnabled(userDTO.isEnabled());

        User updatedUser = userRepository.save(savedUser);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}