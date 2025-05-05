package com.gestion.library.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;


import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    public Set<User> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(Set<User> borrowers) {
        this.borrowers = borrowers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must be at most 150 characters")
    private String title;

    @NotBlank(message = "ISBN is required")
    @Size(max = 30, message = "ISBN must be at most 30 characters")
    private String isbn;

    @Min(value = 1450, message = "Published year must be after 1450")
    @Max(value = 2100, message = "Published year must be before 2100")
    private int publishedYear;

    // You can make coverImage optional, or add validation if needed
    private String coverImage;

    @Size(max = 2000, message = "Description must be at most 2000 characters")
    private String description;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToMany(mappedBy = "borrowedBooks")
    @JsonIgnore
    private Set<User> borrowers;

    public String getCoverImage() {
        return coverImage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
