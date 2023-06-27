package com.example.storeeverything.repository;

import com.example.storeeverything.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByAuthorName(String username);
    @Query("SELECT i FROM Note i WHERE i.isShared = true ORDER BY i.createdDate DESC")
    List<Note> getSharedNotes();
    Optional<Note> findById(Long id);
}
