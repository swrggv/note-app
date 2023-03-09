package com.example.noteapp.models.mappers;

import com.example.noteapp.models.User;
import com.example.noteapp.models.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
