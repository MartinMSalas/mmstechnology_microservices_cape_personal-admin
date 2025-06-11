package com.example.userservice.service;

import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.dto.UserUpdateRequestDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException; // Use jakarta.persistence
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto userCreateDto) {
        if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + userCreateDto.getEmail() + " already exists.");
        }
        User user = new User();
        user.setNombreCompleto(userCreateDto.getNombreCompleto());
        user.setEmail(userCreateDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setRol(userCreateDto.getRol());
        user.setEstaActivo(userCreateDto.getEstaActivo() != null ? userCreateDto.getEstaActivo() : true);
        // @PrePersist will handle creadoEn and actualizadoEn
        User savedUser = userRepository.save(user);
        return mapToUserResponseDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return mapToUserResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return mapToUserResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UUID id, UserUpdateRequestDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (userUpdateDto.getNombreCompleto() != null) {
            user.setNombreCompleto(userUpdateDto.getNombreCompleto());
        }
        if (userUpdateDto.getEmail() != null) {
            // Check if email is being changed and if the new one already exists
            if (!user.getEmail().equals(userUpdateDto.getEmail()) && userRepository.findByEmail(userUpdateDto.getEmail()).isPresent()) {
                 throw new IllegalArgumentException("New email " + userUpdateDto.getEmail() + " is already taken.");
            }
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            // Add password complexity validation if needed
            user.setPasswordHash(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
        if (userUpdateDto.getRol() != null) {
            user.setRol(userUpdateDto.getRol());
        }
        if (userUpdateDto.getEstaActivo() != null) {
            user.setEstaActivo(userUpdateDto.getEstaActivo());
        }
        // @PreUpdate will handle actualizadoEn
        User updatedUser = userRepository.save(user);
        return mapToUserResponseDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponseDto mapToUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setNombreCompleto(user.getNombreCompleto());
        dto.setEmail(user.getEmail());
        dto.setRol(user.getRol());
        dto.setEstaActivo(user.getEstaActivo());
        dto.setUltimoLoginEn(user.getUltimoLoginEn());
        dto.setCreadoEn(user.getCreadoEn());
        dto.setActualizadoEn(user.getActualizadoEn());
        return dto;
    }
}
