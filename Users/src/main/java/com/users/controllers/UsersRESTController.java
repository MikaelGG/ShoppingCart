package com.users.controllers;

import com.users.dto.UsersDTO;
import com.users.models.UserTypeModel;
import com.users.models.UsersModel;
import com.users.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @GetMapping("/email")
    public ResponseEntity<UsersModel> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getUserByEmail(email));
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<List<UsersModel>> getAdmins(@PathVariable UserTypeModel id) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAdmins(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateUser(@PathVariable Long id, @RequestBody UsersDTO userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.updateUser(id, userDetails));
    }

    @PutMapping("/admins/{id}")
    public ResponseEntity<UsersModel> updateAdmin(@PathVariable Long id, @RequestBody UsersDTO userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.updateAdmin(id, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.deleteUser(id));
    }

}
