package com.example.noteapp.controllers;

import com.example.noteapp.models.dto.NoteDto;
import com.example.noteapp.services.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NoteController.class)
class NoteControllerTest {
    @MockBean
    private NoteService noteService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    private NoteDto noteDto;
    private static final long USER_ID = 1;
    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        noteDto = new NoteDto(1, "title", "description", "owner", DATE_TIME);
    }

    @Test
    void addNote() throws Exception {
        Mockito.when(noteService.addNote(any(), anyLong()))
                .thenReturn(noteDto);
        mvc.perform(post("/notes")
                        .content(mapper.writeValueAsString(noteDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("User-Id", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(noteDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(noteDto.getTitle()), String.class))
                .andExpect(jsonPath("$.description", is(noteDto.getDescription()), String.class))
                .andExpect(jsonPath("$.owner", is(noteDto.getOwner()), String.class))
                .andExpect(jsonPath("$.created", startsWith(DATE_TIME.format(FORMAT)), String.class));
    }

    @Test
    void deleteNote() throws Exception {
        mvc.perform(delete("/notes/{idNote}", noteDto.getId())
                        .header("User-Id", USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    void patchNote() throws Exception {
        Mockito.when(noteService.patchNote(anyLong(), any(), anyLong()))
                .thenReturn(noteDto);
        mvc.perform(patch("/notes/{idNote}", noteDto.getId())
                        .content(mapper.writeValueAsString(noteDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("User-Id", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(noteDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(noteDto.getTitle()), String.class))
                .andExpect(jsonPath("$.description", is(noteDto.getDescription()), String.class))
                .andExpect(jsonPath("$.owner", is(noteDto.getOwner()), String.class))
                .andExpect(jsonPath("$.created", startsWith(DATE_TIME.format(FORMAT)), String.class));
    }

    @Test
    void getAllNotes() throws Exception {
        Mockito.when(noteService.getAllNotes(USER_ID))
                .thenReturn(List.of(noteDto));
        mvc.perform(get("/notes")
                        .content(mapper.writeValueAsString(noteDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("User-Id", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(noteDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].title", is(noteDto.getTitle()), String.class))
                .andExpect(jsonPath("$[0].description", is(noteDto.getDescription()), String.class))
                .andExpect(jsonPath("$[0].owner", is(noteDto.getOwner()), String.class))
                .andExpect(jsonPath("$[0].created", startsWith(DATE_TIME.format(FORMAT)), String.class));
    }

    @Test
    void searchNotes() throws Exception {
        Mockito.when(noteService.searchNotes(any(String.class), anyLong()))
                .thenReturn(List.of(noteDto));
        mvc.perform(get("/notes/search")
                        .content(mapper.writeValueAsString(noteDto))
                        .param("text", "text")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("User-Id", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(noteDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].title", is(noteDto.getTitle()), String.class))
                .andExpect(jsonPath("$[0].description", is(noteDto.getDescription()), String.class))
                .andExpect(jsonPath("$[0].owner", is(noteDto.getOwner()), String.class))
                .andExpect(jsonPath("$[0].created", startsWith(DATE_TIME.format(FORMAT)), String.class));
    }
}