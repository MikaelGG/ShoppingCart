package com.users.services;

import com.users.models.UsersModel;
import com.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsersModel> getAllUsers() {
        if (usersRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No users found");
        }
        return usersRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UsersModel getUser(long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    @Transactional
    public UsersModel updateUser(Long id, UsersModel userDeatils) {
        UsersModel existingUser = usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
        existingUser.setFullName(userDeatils.getFullName());
        existingUser.setPhoneNumber(userDeatils.getPhoneNumber());
        existingUser.setEmail(userDeatils.getEmail());
        existingUser.setPassword(userDeatils.getPassword());
        existingUser.setUserType(userDeatils.getUserType());
        return usersRepository.save(existingUser);
    }

    @Transactional
    public String deleteUser(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
        }
        usersRepository.deleteById(id);
        return "User deleted successfully with id: " + id;
    }


}
