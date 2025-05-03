package com.gestion.library.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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

    private String title;
    private String isbn;

    private int publishedYear;
    private String coverImage;
    @Column(length = 2000) // Optional, to allow longer text
    private String description;// path to the uploaded image
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
