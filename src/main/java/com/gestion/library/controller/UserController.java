package com.gestion.library.controller;

import com.gestion.library.entities.Book;
import com.gestion.library.entities.User;
import com.gestion.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

// UserController.java
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Basic validation
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login"; // We'll create login page next
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.findByUsername(username);
        Set<Book> borrowedBooks = user.getBorrowedBooks();

        model.addAttribute("borrowedBooks", borrowedBooks);
        model.addAttribute("user", user);
        return "dashboard";
    }

}
