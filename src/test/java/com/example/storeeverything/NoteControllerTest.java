package com.example.storeeverything;

import com.example.storeeverything.controller.NoteController;
import com.example.storeeverything.dto.CategoryDto;
import com.example.storeeverything.dto.NoteDto;
import com.example.storeeverything.service.CategoryService;
import com.example.storeeverything.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NoteControllerTest {
    @Mock
    private NoteService noteService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteNote() {
        Long id = 1L;
        String expectedRedirect = "redirect:/user-notes";

        String result = noteController.deleteNote(id);

        verify(noteService, times(1)).deleteById(id);
        assertEquals(expectedRedirect, result);
    }

    @Test
    public void testCreateNoteWithErrors() {
        Model model = mock(Model.class);
        BindingResult bindingResult = mock(BindingResult.class);
        NoteDto noteDto = new NoteDto();
        List<CategoryDto> categories = new ArrayList<>();
        String expectedViewName = "/add-note";

        when(bindingResult.hasErrors()).thenReturn(true);
        when(categoryService.getCategories()).thenReturn(categories);

        String result = noteController.createNote(noteDto, bindingResult, model);

        verify(model, times(1)).addAttribute(eq("note"), eq(noteDto));
        verify(model, times(1)).addAttribute(eq("categories"), eq(categories));
        assertEquals(expectedViewName, result);
    }

    @Test
    public void testCreateNoteWithoutErrors() {
        Model model = mock(Model.class);
        BindingResult bindingResult = mock(BindingResult.class);
        NoteDto noteDto = new NoteDto();
        List<CategoryDto> categories = new ArrayList<>();
        String expectedRedirect = "redirect:/user-notes";

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = noteController.createNote(noteDto, bindingResult, model);

        verify(noteService, times(1)).saveNote(noteDto);
        assertEquals(expectedRedirect, result);
    }
}
