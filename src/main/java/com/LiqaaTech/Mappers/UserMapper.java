package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.UserDTO;
import com.LiqaaTech.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    @Autowired
    @Lazy
    private EventMapper eventMapper;
    @Autowired
    @Lazy
    private RegistrationMapper registrationMapper;

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setEnabled(user.isEnabled());

        if (user.getRegistrations() != null) {
            userDTO.setRegistrationsDTO(registrationMapper.toDTOListWithoutUser(user.getRegistrations()));
        }

        if (user.getOrganizedEvents() != null) {
            userDTO.setOrganizedEventsDTO(eventMapper.toDTOList(user.getOrganizedEvents()));
        }

        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        user.setEnabled(userDTO.isEnabled());

        if (userDTO.getRegistrationsDTO() != null) {
            user.setRegistrations(registrationMapper.toEntityListWithoutUser(userDTO.getRegistrationsDTO()));
        }

        if (userDTO.getOrganizedEventsDTO() != null) {
            user.setOrganizedEvents(eventMapper.toEntityList(userDTO.getOrganizedEventsDTO()));
        }

        return user;
    }

    public List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        return users.stream().map(this::toDTO).toList();
    }

    public List<User> toEntityList(List<UserDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        return dtos.stream().map(this::toEntity).toList();
    }

    public UserDTO toDTOWithoutEvents(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setEnabled(user.isEnabled());
        return userDTO;
    }

    public User toEntityWithoutEvents(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        user.setEnabled(userDTO.isEnabled());
        return user;
    }
}