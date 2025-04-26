package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.UserDTO;
import com.LiqaaTech.Entities.User;
import com.LiqaaTech.Enums.Role;
import com.LiqaaTech.Exceptions.NotFoundException;
import com.LiqaaTech.Mappers.UserMapper;
import com.LiqaaTech.Repositories.UserRepository;
import com.LiqaaTech.Services.Interf.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        if(user.getRole() == null){
            user.setRole(Role.PARTICIPANT);
        }
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new NotFoundException("No Users found");
        }
        return userMapper.toDTOList(users);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User savedUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
        savedUser.setEmail(userDTO.getEmail());
        savedUser.setUsername(userDTO.getUsername());
        savedUser.setRole(userDTO.getRole());
        savedUser.setEnabled(userDTO.isEnabled());
        userRepository.save(savedUser);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
        userRepository.delete(user);
    }
}
