package com.example.noteapp.services.impl;

import com.example.noteapp.exeptions.ModelNotFoundException;
import com.example.noteapp.exeptions.NoRootException;
import com.example.noteapp.models.Note;
import com.example.noteapp.models.User;
import com.example.noteapp.models.dto.NewNoteDto;
import com.example.noteapp.models.dto.NoteDto;
import com.example.noteapp.models.mappers.NoteMapper;
import com.example.noteapp.repository.NoteRepository;
import com.example.noteapp.repository.UserRepository;
import com.example.noteapp.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteMapper noteMapper;

    @Override
    @Transactional
    public NoteDto addNote(NewNoteDto newNoteDto, long idUser) {
        User owner = fromOptionalToUser(idUser);
        Note note = noteMapper.toNote(newNoteDto, owner);
        note.setId(0);
        note = noteRepository.saveAndFlush(note);
        return noteMapper.toNoteDto(note);
    }

    @Override
    @Transactional
    public void deleteNote(long idNote, long idUser) {
        Note note = fromOptionalToNote(idNote);
        if (note.getOwner().getId() == idUser) {
            noteRepository.deleteById(idNote);
        } else {
            throw new NoRootException(String.format("User %d is not owner", idUser));
        }
    }

    @Override
    public List<NoteDto> getAllNotes(long idUser) {
        User owner = fromOptionalToUser(idUser);
        List<Note> allNotes = noteRepository.findAllByOwner(owner);
        return noteMapper.toListNoteDto(allNotes);
    }

    @Override
    @Transactional
    public NoteDto patchNote(long idNote, NewNoteDto newNoteDto, long idUser) {
        Note note = fromOptionalToNote(idNote);
        if (note.getOwner().getId() == idUser) {
            updateNote(note, newNoteDto);
            return noteMapper.toNoteDto(note);
        } else {
            throw new NoRootException(String.format("User %d is not owner", idUser));
        }
    }

    private Note updateNote(Note note, NewNoteDto newNoteDto) {
        if (newNoteDto.getTitle() != null) {
            note.setTitle(newNoteDto.getTitle());
        }
        if (newNoteDto.getDescription() != null) {
            note.setDescription(newNoteDto.getDescription());
        }
        return note;
    }

    @Override
    public List<NoteDto> searchNotes(String text, long idUser) {
        User user = fromOptionalToUser(idUser);
        List<Note> searchedNotes =
                noteRepository.findAllByTitleContainingIgnoreCaseAndOwner(text, user);
        return noteMapper.toListNoteDto(searchedNotes);
    }

    @Override
    public NoteDto getNote(long id) {
        return noteMapper.toNoteDto(fromOptionalToNote(id));
    }

    private Note fromOptionalToNote(long idNote) {
        return noteRepository.findById(idNote).orElseThrow(
                () -> new ModelNotFoundException(String.format("Note with id %d not found", idNote)));
    }

    private User fromOptionalToUser(long idUser) {
        return userRepository.findById(idUser).orElseThrow(
                () -> new ModelNotFoundException(String.format("User with id %d not found", idUser)));
    }
}
