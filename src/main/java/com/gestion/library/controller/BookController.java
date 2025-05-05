package com.gestion.library.controller;

import com.gestion.library.entities.Author;
import com.gestion.library.entities.Book;
import com.gestion.library.entities.User;
import com.gestion.library.service.AuthorService;
import com.gestion.library.service.BookService;

import com.gestion.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    //private final String UPLOAD_DIR = "src/main/resources/static";
    private static final String UPLOAD_DIR = "C:/uploads/";

    @Autowired
    UserService userService;


    // List all books
    @GetMapping
    public String listBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(keyword));
            model.addAttribute("searchMode", true);
        } else {
            Page<Book> bookPage = bookService.getAllBooks(page, size);
            model.addAttribute("books", bookPage.getContent());
            model.addAttribute("totalPages", bookPage.getTotalPages());
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageSizes", List.of(5, 10, 20));

        return "listbook";
    }




    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "addbook";  // matches addbook.html in templates/
    }




    // Save book with image upload
    @PostMapping("/save")
    public String saveBook(
            @ModelAttribute Book book,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("authorId") Long authorId  // Remove required=false
    ) throws IOException {
        // Image handling
        if (!imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent());  // Ensure directory exists
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            book.setCoverImage(fileName);
        }

        // Author validation
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        Author author = authorService.getAuthorById(authorId);
        if (author == null) {
            throw new IllegalArgumentException("Invalid author ID: " + authorId);
        }
        book.setAuthor(author);

        bookService.saveBook(book);
        return "redirect:/books";
    }




    // Edit book
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "addbook";  // reuse addbook.html
    }

    // Delete book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }


    @GetMapping("/borrow/{id}")
    public String borrowBook(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();


        User user = userService.findByUsername(username);
        bookService.borrowBook(id, user.getId());

        return "redirect:/users/dashboard";
    }


    @GetMapping("/details/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            // Handle book not found, e.g. redirect or show error page
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "bookdetails"; // name of the Thymeleaf template for details
    }

}
