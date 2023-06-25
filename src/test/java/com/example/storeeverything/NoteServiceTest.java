package com.example.storeeverything;

import com.example.storeeverything.dto.NoteDto;
import com.example.storeeverything.model.Category;
import com.example.storeeverything.model.Note;
import com.example.storeeverything.model.User;
import com.example.storeeverything.repository.NoteRepository;
import com.example.storeeverything.service.CategoryService;
import com.example.storeeverything.service.NoteService;
import com.example.storeeverything.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        noteService.deleteById(id);

        verify(noteRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetUserNotes() {
        User user = new User();
        user.setName("TestUser");
        Note note = new Note();
        note.setTitle("Test Note");
        List<Note> notes = new ArrayList<>();
        notes.add(note);

        when(userService.getCurrentUser()).thenReturn(user);
        when(noteRepository.findByAuthorName("TestUser")).thenReturn(notes);

        List<NoteDto> result = noteService.getUserNotes();

        verify(noteRepository, times(1)).findByAuthorName("TestUser");
        assertEquals(1, result.size());
        assertEquals("Test Note", result.get(0).getTitle());
    }

    @Test
    public void testGetSharedNotes() {
        Note note = new Note();
        note.setTitle("Test Note");
        List<Note> notes = new ArrayList<>();
        notes.add(note);

        when(noteRepository.getSharedNotes()).thenReturn(notes);

        List<NoteDto> result = noteService.getSharedNotes();

        verify(noteRepository, times(1)).getSharedNotes();
        assertEquals(1, result.size());
        assertEquals("Test Note", result.get(0).getTitle());
    }
}
