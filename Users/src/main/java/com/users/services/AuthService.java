package com.users.services;

import com.users.dto.SignInDTO;
import com.users.jwt.JwtBlackList;
import com.users.jwt.JwtService;
import com.users.models.UsersModel;
import com.users.repositories.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    JwtBlackList jwtBlackList;

    @Transactional
    public UsersModel signUp(UsersModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    @Transactional
    public String signIn(SignInDTO data) {
        UsersModel user = usersRepository.findByEmail(data.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + data.getEmail()));

        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials");
        }

        String token = jwtService.generateToken(user);

        return token;
    }

    @Transactional(readOnly = true)
    public String logOut(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            jwtBlackList.blackListToken(jwt);

            SecurityContextHolder.clearContext();

            return "successful logout";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No token found");
        }
    }

}
