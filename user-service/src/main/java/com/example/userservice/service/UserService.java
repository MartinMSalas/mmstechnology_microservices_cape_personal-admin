package com.example.userservice.service;

import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.dto.UserUpdateRequestDto;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto createUser(UserCreateRequestDto userCreateDto);
    UserResponseDto getUserById(UUID id);
    UserResponseDto getUserByEmail(String email);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(UUID id, UserUpdateRequestDto userUpdateDto);
    void deleteUser(UUID id);
}
