package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.UserDTO;
import com.LiqaaTech.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    @Autowired
    private EventMapper EventMapper;
    @Autowired
    private RegistrationMapper RegistrationMapper;

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setRegistrationsDTO(RegistrationMapper.toDTOList(user.getRegistrations()));
        userDTO.setOrganizedEventsDTO(EventMapper.toDTOList(user.getOrganizedEvents()));
        return userDTO;
    }
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        user.setEnabled(userDTO.isEnabled());
        user.setRegistrations(RegistrationMapper.toEntityList(userDTO.getRegistrationsDTO()));
        user.setOrganizedEvents(EventMapper.toEntityList(userDTO.getOrganizedEventsDTO()));
        return user;
    }

    public List<UserDTO> toDTOList(List<User> users) {
        return users.stream().map(this::toDTO).toList();
    }
    public List<User> toEntityList(List<UserDTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

}
