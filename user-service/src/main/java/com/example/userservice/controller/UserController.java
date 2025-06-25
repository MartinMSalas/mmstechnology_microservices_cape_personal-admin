package com.example.userservice.controller;

import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.dto.UserUpdateRequestDto;
import com.example.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value; // For config property
import org.springframework.cloud.context.config.annotation.RefreshScope; // For dynamic config updates

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users") // Base path for user operations
@RefreshScope // Allows dynamic updates of @Value properties from Config Server
public class UserController {

    private final UserService userService;

    @Value("${user-service.welcome-message:Hello default user!}") // From config-server or default
    private String welcomeMessage;

    @Value("${global.message:Default global message}")
    private String globalMessage;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> getWelcomeMessage() {
        return ResponseEntity.ok("Service Message: " + welcomeMessage + " | Global Message: " + globalMessage);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto userCreateDto) {
        UserResponseDto createdUser = userService.createUser(userCreateDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequestDto userUpdateDto) {
        UserResponseDto updatedUser = userService.updateUser(id, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Basic error handling example (can be moved to a @ControllerAdvice)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
