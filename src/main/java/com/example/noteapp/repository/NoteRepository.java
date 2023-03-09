package com.example.noteapp.repository;

import com.example.noteapp.models.Note;
import com.example.noteapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    boolean existsById(long id);

    boolean existsByIdAndOwner(long idNote, User user);

    List<Note> findAllByOwner(User owner);

    List<Note> findAllByTitleContainingIgnoreCaseAndOwner(String text, User user);
}
