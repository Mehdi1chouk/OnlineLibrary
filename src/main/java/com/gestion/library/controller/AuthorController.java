package com.gestion.library.controller;


import com.gestion.library.entities.Author;
import com.gestion.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // Display list of authors
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "listauthor";  // Thymeleaf template: templates/authors/list.html
    }

    // Show form to add new author
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        return "addauthor";  // Thymeleaf template: templates/authors/add.html
    }

    // Save author (handle form submission)
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute Author author) {
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    // Show form to edit author
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        if (author == null) {
            return "redirect:/authors"; // or show error page
        }
        model.addAttribute("author", author);
        return "addauthor";
    }
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
