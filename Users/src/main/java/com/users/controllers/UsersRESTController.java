package com.users.controllers;

import com.users.models.UsersModel;
import com.users.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersRESTController {

    @Autowired
    UsersService usersService;

    @GetMapping
    public ResponseEntity<List<UsersModel>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersModel> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersModel> updateUser(@PathVariable Long id, @RequestBody UsersModel userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.updateUser(id, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.deleteUser(id));
    }

}
