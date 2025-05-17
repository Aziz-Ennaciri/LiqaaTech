package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.DTOs.SignupRequest;
import com.LiqaaTech.DTOs.UserDTO;
import com.LiqaaTech.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO getUserByUsername(String username);
    User getUserByEmail(String email);
    UserDetails loadUserByUsername(String username);
    Page<UserDTO> getAllUsers(Pageable pageable);
    long countActiveUsers();
    long countOrganizers();
    User save(User user);

    // Additional methods for MVC controller
    UserDTO login(String username, String password);
    UserDTO registerUser(SignupRequest signupRequest);
    UserDTO updateUserProfile(String username, UserDTO userDTO);
    UserDTO updateProfilePicture(String username, MultipartFile file);
    void changePassword(String username, String currentPassword, String newPassword);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserDTO findByUsername(String username);
    UserDTO findByEmail(String email);
    int getActiveUsersCount();
    int getOrganizerCount();
}
