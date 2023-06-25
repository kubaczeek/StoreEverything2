
package com.example.storeeverything.controller;

import com.example.storeeverything.dto.CategoryDto;
import com.example.storeeverything.dto.NoteDto;
import com.example.storeeverything.service.CategoryService;
import com.example.storeeverything.service.NoteService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    public NoteController(NoteService noteService, CategoryService categoryService) {
        this.noteService = noteService;
        this.categoryService = categoryService;
    }

    @GetMapping({"notes/delete/{id}"})
    public String deleteNote(@PathVariable("id") Long id) {
        this.noteService.deleteById(id);
        logger.info("notes/delete/" + id);
        return "redirect:/user-notes";
    }

    @GetMapping({"/add-note"})
    public String showAddForm(Model model) {
        List<CategoryDto> categories = this.categoryService.getCategories();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        model.addAttribute("note", new NoteDto());
        model.addAttribute("categories", categories);
        logger.info("/notes");
        return "add-note";
    }

    @PostMapping({"/notes/add"})
    public String createNote(@ModelAttribute("note") @Valid NoteDto note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<CategoryDto> categories = this.categoryService.getCategories();
            model.addAttribute("note", note);
            model.addAttribute("categories", categories);
            return "/add-note";
        } else {
            this.noteService.saveNote(note);
            logger.info("/notes/add");
            return "redirect:/user-notes";
        }
    }

    @GetMapping({"/user-notes"})
    public String showCurrentUserNotes(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<NoteDto> notes = this.noteService.getUserNotes();
        model.addAttribute("username", username);
        model.addAttribute("notes", notes);
        logger.info("/user-notes");
        return "user-notes";
    }

    @GetMapping({"/shared-notes"})
    public String showSharedNotes(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<NoteDto> notes = this.noteService.getSharedNotes();
        model.addAttribute("notes", notes);
        model.addAttribute("username", username);
        logger.info("/shared-notes");
        return "shared-notes";
    }
}
