package com.example.noteapp.services;

import com.example.noteapp.models.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto getUser(long idUser);
}
