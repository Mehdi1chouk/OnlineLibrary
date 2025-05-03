package com.gestion.library.controller;

import com.gestion.library.entities.Book;
import com.gestion.library.entities.User;
import com.gestion.library.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "User management endpoints")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // Other user operations...

    @GetMapping("/{id}/borrowed-books")
    public ResponseEntity<Set<Book>> getUserBorrowedBooks(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ?
                ResponseEntity.ok(user.getBorrowedBooks()) :
                ResponseEntity.notFound().build();
    }
}
