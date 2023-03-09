package com.example.noteapp.services;

import com.example.noteapp.models.dto.NewNoteDto;
import com.example.noteapp.models.dto.NoteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    NoteDto addNote(NewNoteDto newNoteDto, long idUser);

    void deleteNote(long idNote, long idUser);

    NoteDto patchNote(long idNote, NewNoteDto newNoteDto, long idUser);

    List<NoteDto> getAllNotes(long idUser);

    List<NoteDto> searchNotes(String text, long idUser);

    NoteDto getNote(long id);
}
