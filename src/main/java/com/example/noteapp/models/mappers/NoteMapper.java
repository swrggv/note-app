package com.example.noteapp.models.mappers;

import com.example.noteapp.models.Note;
import com.example.noteapp.models.User;
import com.example.noteapp.models.dto.NewNoteDto;
import com.example.noteapp.models.dto.NoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Mapping(target = "owner", source = "owner")
    @Mapping(ignore = true, target = "owner.id")
    Note toNote(NewNoteDto newNoteDto, User owner);

    @Mapping(target = "owner", source = "owner.name")
    NoteDto toNoteDto(Note note);

    List<NoteDto> toListNoteDto(List<Note> allNotes);
}
