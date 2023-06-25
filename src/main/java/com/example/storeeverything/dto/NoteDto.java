package com.example.storeeverything.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    private Long id;

    @NotEmpty(message = "Title can't be empty")
    @Size(min = 3, max = 20, message = "Title must be between 3 and 20 characters")
    private String title;

    @NotEmpty(message = "Details can't be empty")
    @Size(min = 5, max = 500, message = "Details must be between 5 and 500 characters")
    private String details;

    @NotEmpty(message = "Category can't be empty")
    private String categoryName;
    private Boolean isShared;
    private LocalDate createdDate;
    private String authorName;

}
