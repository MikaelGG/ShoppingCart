package com.users.controllers;

import com.users.models.UserTypeModel;
import com.users.services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-types")
public class UserTypeRESTController {

    @Autowired
    UserTypeService userTypeService;

    @GetMapping
    public ResponseEntity<List<UserTypeModel>> getAllUserTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(userTypeService.getAllUserTypes());
    }

    @PostMapping
    public ResponseEntity<UserTypeModel> createUserType(@RequestBody UserTypeModel userTypeModelU) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userTypeService.createUserType(userTypeModelU));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTypeModel> updateUserType(@PathVariable Long id, @RequestBody UserTypeModel userTypeModelU) {
        return ResponseEntity.status(HttpStatus.OK).body(userTypeService.updateUserType(id, userTypeModelU));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserType(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userTypeService.deleteUserType(id));
    }


}
