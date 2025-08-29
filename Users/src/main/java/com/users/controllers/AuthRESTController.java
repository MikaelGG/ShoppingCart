package com.users.controllers;

import com.users.dto.SignInDTO;
import com.users.models.UsersModel;
import com.users.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRESTController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UsersModel> createUser(@RequestBody UsersModel user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(user));
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid SignInDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(data));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.logOut(request));
    }


}
