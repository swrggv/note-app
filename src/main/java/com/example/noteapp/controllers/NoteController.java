package com.example.noteapp.controllers;

import com.example.noteapp.models.dto.NewNoteDto;
import com.example.noteapp.models.dto.NoteDto;
import com.example.noteapp.services.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/notes")
@AllArgsConstructor
@Slf4j
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public NoteDto addNote(@RequestBody NewNoteDto newNoteDto,
                           @RequestHeader("User-Id") long idUser) {
        NoteDto result = noteService.addNote(newNoteDto, idUser);
        log.info("Note {} was added by user {}", newNoteDto, idUser);
        return result;
    }

    @DeleteMapping(path = "{idNote}")
    public void deleteNote(@PathVariable long idNote,
                           @RequestHeader("User-Id") long idUser) {
        noteService.deleteNote(idNote, idUser);
        log.info("Note {} was deleted by user {}", idNote, idUser);
    }

    @PatchMapping(path = "{idNote}")
    public NoteDto patchNote(@PathVariable long idNote,
                             @RequestBody NewNoteDto newNoteDto,
                             @RequestHeader("User-Id") long idUser) {
        NoteDto result = noteService.patchNote(idNote, newNoteDto, idUser);
        log.info("Note {} was updated by user {}", idNote, idUser);
        return result;
    }

    @GetMapping()
    public List<NoteDto> getAllNotes(@RequestHeader("User-Id") long idUser) {
        List<NoteDto> result = noteService.getAllNotes(idUser);
        log.info("Get all notes by user {}", idUser);
        return result;
    }

    @GetMapping("/search")
    public List<NoteDto> searchNotes(@RequestParam String text,
                                     @RequestHeader("User-Id") long idUser) {
        List<NoteDto> result = noteService.searchNotes(text, idUser);
        log.info("Search notes with text pattern {} by user {}", text, idUser);
        return result;
    }
}
