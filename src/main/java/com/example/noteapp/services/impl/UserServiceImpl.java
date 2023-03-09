package com.example.noteapp.services.impl;

import com.example.noteapp.exeptions.ModelAlreadyExist;
import com.example.noteapp.exeptions.ModelNotFoundException;
import com.example.noteapp.models.Note;
import com.example.noteapp.models.User;
import com.example.noteapp.models.dto.NewNoteDto;
import com.example.noteapp.models.dto.UserDto;
import com.example.noteapp.models.mappers.UserMapper;
import com.example.noteapp.repository.NoteRepository;
import com.example.noteapp.repository.UserRepository;
import com.example.noteapp.services.NoteService;
import com.example.noteapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final NoteService noteService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        if (userRepository.existsByName(userDto.getName())) {
            throw new ModelAlreadyExist(String.format("User with name %s already exist", userDto.getName()));
        } else {
            User user = userRepository.save(userMapper.toUser(userDto));
            createFirstNote(user);
            return userMapper.toUserDto(user);
        }
    }

    private void createFirstNote(User user) {
        NewNoteDto newNoteDto = new NewNoteDto("My first note!", "Hello world");
        noteService.addNote(newNoteDto, user.getId());
    }

    @Override
    public UserDto getUser(long idUser) {
        return userMapper.toUserDto(fromOptionalToUser(idUser));
    }

    private User fromOptionalToUser(long idUser) {
        return userRepository.findById(idUser).orElseThrow(
                () -> new ModelNotFoundException(String.format("User with id %s not found", idUser))
        );
    }
}
