package com.example.demo.service.impl;

import com.example.demo.dto.request.SaveUserRequestDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.demo.dto.request.UpdateUserRequestDTO;
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
    private final UserMapper userMapper;

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Role getRoleById(long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    @Override
    public long saveUser(SaveUserRequestDTO userRequestDTO) {
        Role role = getRoleById(userRequestDTO.getRoleId());

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
    public void updateUser(long userId, UpdateUserRequestDTO user) {
        User userUpdate = getUserById(userId);
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPassword(user.getPassword());
        userUpdate.setStatus(user.getStatus());
        userRepository.save(userUpdate);
    }

    @Override
    public void changeStatus(long userId, String userStatus) {

    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public UserResponseDTO getUser(long userId) {
        User user = getUserById(userId);
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public Page<UserResponseDTO> getUsers(long page, long size, String sortBy, String sortDirection) {
        if (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC")) {
            sortDirection = "ASC";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        Pageable pageable = PageRequest.of((int) page, (int) size, sort);
        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(userMapper::toUserResponseDTO);
    }
}