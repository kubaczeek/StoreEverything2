package com.example.storeeverything.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = false, unique = false)
    private String details;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(nullable = false, unique = false)
    private String authorName;

    @Column(nullable = false)
    private LocalDate createdDate;

    @Column(nullable = false, unique = false)
    private Boolean isShared;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, unique = false)
    private String categoryName;
}