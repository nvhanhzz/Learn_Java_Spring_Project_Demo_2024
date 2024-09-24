package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public long saveUser(UserRequestDTO userRequestDTO) {
        Role role = getRoleById(userRequestDTO.getRoleId());

        if (role == null) {
            // throw exception here
        }

        User userEntity = User.builder()
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .status(userRequestDTO.getStatus())
                .role(role)
                .build();

        User savedUser = userRepository.save(userEntity);

        return savedUser.getId();
    }

    @Override
    public void updateUser(long userId, UserRequestDTO user) {

    }

    @Override
    public void changeStatus(long userId, String userStatus) {

    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public UserResponseDTO getUser(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            // throw new UserNotFoundException("User with ID " + userId + " not found.");
            return null;
        }

        return mapToUserResponseDTO(user);
    }

    @Override
    public Page<UserResponseDTO> getUsers(long page, long size, String sortBy, String sortDirection) {
        if (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC")) {
            sortDirection = "ASC";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        Pageable pageable = PageRequest.of((int) page, (int) size, sort);
        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(this::mapToUserResponseDTO);
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    private Role getRoleById(long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roleId(user.getRole().getId())
                .build();
    }
}