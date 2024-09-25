package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.id", target = "roleId")
    UserResponseDTO toUserResponseDTO(User user);
}