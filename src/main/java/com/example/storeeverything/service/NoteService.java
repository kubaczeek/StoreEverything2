
package com.example.storeeverything.service;

import com.example.storeeverything.dto.NoteDto;
import com.example.storeeverything.model.Category;
import com.example.storeeverything.model.Note;
import com.example.storeeverything.model.User;
import com.example.storeeverything.repository.NoteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public NoteService(NoteRepository noteRepository, UserService userService, CategoryService categoryService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public void saveNote(NoteDto noteDto) {
        User user = this.userService.getCurrentUser();
        Category category = this.categoryService.getCategoryByName(noteDto.getCategoryName());
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setDetails(noteDto.getDetails());
        note.setAuthorName(user.getName());
        note.setCreatedDate(LocalDate.now());
        note.setIsShared(noteDto.getIsShared());
        note.setAuthor(user);
        note.setCategory(category);
        note.setCategoryName(noteDto.getCategoryName());
        this.noteRepository.save(note);
    }

    public void updateNote(NoteDto noteDto) {
        Note note = noteRepository.findById(noteDto.getId()).orElse(null);
        User user = this.userService.getCurrentUser();
        Category category = this.categoryService.getCategoryByName(noteDto.getCategoryName());
        note.setTitle(noteDto.getTitle());
        note.setDetails(noteDto.getDetails());
        note.setAuthorName(user.getName());
        note.setCreatedDate(LocalDate.now());
        note.setIsShared(noteDto.getIsShared());
        note.setAuthor(user);
        note.setCategory(category);
        note.setCategoryName(noteDto.getCategoryName());
        this.noteRepository.save(note);
    }

    public void deleteById(Long id) {
        this.noteRepository.deleteById(id);
    }

    public List<NoteDto> getUserNotes() {
        User user = this.userService.getCurrentUser();
        List<Note> notes = this.noteRepository.findByAuthorName(user.getName());
        return this.mapNotesToDtos(notes);
    }

    public List<NoteDto> getSharedNotes() {
        List<Note> notes = this.noteRepository.getSharedNotes();
        return this.mapNotesToDtos(notes);
    }

    public NoteDto findById(Long id) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note == null)
            throw new RuntimeException("Note not found");
        return new NoteDto(note.getId(), note.getTitle(), note.getDetails(), note.getCategoryName(), note.getIsShared(), note.getCreatedDate(), note.getAuthorName());
    }

    List<NoteDto> mapNotesToDtos(List<Note> notes) {
        return notes.stream().map((note) -> {
            NoteDto noteDto = new NoteDto();
            noteDto.setId(note.getId());
            noteDto.setTitle(note.getTitle());
            noteDto.setDetails(note.getDetails());
            noteDto.setAuthorName(note.getAuthorName());
            noteDto.setCreatedDate(note.getCreatedDate());
            noteDto.setIsShared(note.getIsShared());
            noteDto.setCategoryName(note.getCategoryName());
            return noteDto;
        }).collect(Collectors.toList());
    }
}
