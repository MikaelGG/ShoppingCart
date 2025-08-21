package com.users.services;

import com.users.models.usersModel;
import com.users.repositories.usersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class usersService {

    @Autowired
    usersRepository usersRepository;

    @Transactional
    public usersModel createUser(usersModel user) {
        return usersRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<usersModel> getAllUsers() {
        if (usersRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No users found");
        }
        return usersRepository.findAll();
    }

    @Transactional(readOnly = true)
    public usersModel getUser(long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    @Transactional
    public usersModel updateUser(Long id, usersModel userDeatils) {
        usersModel existingUser = usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
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
