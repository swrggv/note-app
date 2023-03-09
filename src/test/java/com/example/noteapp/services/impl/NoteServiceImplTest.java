package com.example.noteapp.services.impl;

import com.example.noteapp.models.dto.NewNoteDto;
import com.example.noteapp.models.dto.NoteDto;
import com.example.noteapp.models.dto.UserDto;
import com.example.noteapp.services.NoteService;
import com.example.noteapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = "application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NoteServiceImplTest {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    private NewNoteDto newNoteDto;
    private NoteDto noteDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(1, "user");
        newNoteDto = new NewNoteDto("title", "description");

        userDto = userService.addUser(userDto);
        noteDto = noteService.addNote(newNoteDto, userDto.getId());

        //удаление приветственной заметки, чтобы не мешала
        noteService.deleteNote(1, userDto.getId());
    }

    @Test
    void addNote() {
        assertThat(noteDto.getTitle()).isEqualTo(newNoteDto.getTitle());
        assertThat(noteDto.getDescription()).isEqualTo(newNoteDto.getDescription());
    }

    @Test
    void addNoteWithModelNotFoundException() {
        assertThatThrownBy(() -> noteService.addNote(newNoteDto, -1))
                .hasMessage(String.format("User with id %d not found", -1));
    }

    @Test
    void deleteNote() {
        noteService.deleteNote(noteDto.getId(), userDto.getId());
        List<NoteDto> result = noteService.getAllNotes(userDto.getId());

        assertEquals(0, result.size());
    }

    @Test
    void deleteNoteWithNoRootException() {
        UserDto secondUser = userService.addUser(new UserDto(2, "second user"));

        assertThatThrownBy(() -> noteService.deleteNote(noteDto.getId(), secondUser.getId()))
                .hasMessage(String.format("User %d is not owner", secondUser.getId()));
    }

    @Test
    void deleteNoteWithModelNotFoundException() {
        assertThatThrownBy(() -> noteService.deleteNote(-1, userDto.getId()))
                .hasMessage(String.format(String.format("Note with id %d not found", -1)));
    }

    @Test
    void getAllNotes() {
        List<NoteDto> result = noteService.getAllNotes(userDto.getId());

        assertEquals(1, result.size());
    }

    @Test
    void patchNote() {
        NewNoteDto patchNote = new NewNoteDto("new title", "new description");
        NoteDto result = noteService.patchNote(noteDto.getId(), patchNote, userDto.getId());

        assertThat(result.getTitle()).isEqualTo(patchNote.getTitle());
        assertThat(result.getDescription()).isEqualTo(patchNote.getDescription());
    }

    @Test
    void patchNoteWithNoRootException() {
        UserDto secondUser = userService.addUser(new UserDto(2, "second user"));
        NewNoteDto patchNote = new NewNoteDto("new title", "new description");

        assertThatThrownBy(() -> noteService.patchNote(noteDto.getId(), patchNote, secondUser.getId()))
                .hasMessage(String.format("User %d is not owner", secondUser.getId()));
    }

    @Test
    void searchNote() {
        List<NoteDto> result = noteService.searchNotes("ti", userDto.getId());
        assertThat(result.get(0).getTitle()).isEqualTo(noteDto.getTitle());
        assertThat(result.get(0).getDescription()).isEqualTo(noteDto.getDescription());
    }
}