package com.users.controllers;

import com.users.models.userTypeModel;
import com.users.services.userTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-types")
public class userTypeRESTController {

    @Autowired
    userTypeService userTypeService;

    @GetMapping
    public ResponseEntity<List<userTypeModel>> getAllUserTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(userTypeService.getAllUserTypes());
    }

    @PostMapping
    public ResponseEntity<userTypeModel> createUserType(@RequestBody userTypeModel userTypeModelU) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userTypeService.createUserType(userTypeModelU));
    }

    @PutMapping("/{id}")
    public ResponseEntity<userTypeModel> updateUserType(@PathVariable Long id, @RequestBody userTypeModel userTypeModelU) {
        return ResponseEntity.status(HttpStatus.OK).body(userTypeService.updateUserType(id, userTypeModelU));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserType(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userTypeService.deleteUserType(id));
    }


}
