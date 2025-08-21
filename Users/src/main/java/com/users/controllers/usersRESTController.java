package com.users.controllers;

import com.users.models.usersModel;
import com.users.services.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class usersRESTController {

    @Autowired
    usersService usersService;

    @PostMapping
    public ResponseEntity<usersModel> createUser(@RequestBody usersModel user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<usersModel>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<usersModel> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<usersModel> updateUser(@PathVariable Long id, @RequestBody usersModel userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.updateUser(id, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.deleteUser(id));
    }

}
