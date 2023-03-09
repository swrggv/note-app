package com.example.noteapp.controllers;

import com.example.noteapp.models.dto.UserDto;
import com.example.noteapp.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        UserDto result = userService.addUser(userDto);
        log.info("User with name {} was added", userDto.getName());
        return result;
    }

    @GetMapping("{idUser}")
    public UserDto getUser(@PathVariable long idUser) {
        UserDto result = userService.getUser(idUser);
        log.info("Get user {}", idUser);
        return result;
    }
}
