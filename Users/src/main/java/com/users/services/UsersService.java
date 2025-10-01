package com.users.services;

import com.users.dto.UsersDTO;
import com.users.jwt.JwtService;
import com.users.models.UserTypeModel;
import com.users.models.UsersModel;
import com.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

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

    @Transactional(readOnly = true)
    public UsersModel getUserByEmail(String email) {
        if (!usersRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email: " + email + ", not found");
        }
        Optional<UsersModel> user = usersRepository.findByEmail(email);
        return user.get();
    }

    @Transactional(readOnly = true)
    public List<UsersModel> getAdmins(UserTypeModel id) {
        if (!usersRepository.existsByUserType(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrators not found");
        }
        return usersRepository.findByUserType(id);
    }

    @Transactional
    public HashMap<String, Object> updateUser(Long id, UsersDTO userDeatils) {
        UsersModel existingUser = usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
        existingUser.setFullName(userDeatils.getFullName());
        existingUser.setPhoneNumber(userDeatils.getPhoneNumber());
        existingUser.setEmail(userDeatils.getEmail());
        if (!userDeatils.getCurrentPassword().isEmpty()) {
            if (!passwordEncoder.matches(userDeatils.getCurrentPassword(), existingUser.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password incorrect");
            }
            if (userDeatils.getNewPassword().equals(userDeatils.getRepeatNewPassword())) {
                existingUser.setPassword(passwordEncoder.encode(userDeatils.getNewPassword()));
            }
        }
        existingUser.setUserType(userDeatils.getUserType());
        String newToken = jwtService.generateToken(existingUser);

        HashMap<String, Object> response = new HashMap<>();
        response.put("user edited: ", usersRepository.save(existingUser));
        response.put("newToken", newToken);
        return response;
    }

    @Transactional
    public UsersModel updateAdmin(Long id, UsersDTO userDeatils) {
        UsersModel existingUser = usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
        existingUser.setFullName(userDeatils.getFullName());
        existingUser.setPhoneNumber(userDeatils.getPhoneNumber());
        existingUser.setEmail(userDeatils.getEmail());
        if (!userDeatils.getCurrentPassword().isEmpty() || userDeatils.getCurrentPassword() != null) {
            if (!passwordEncoder.matches(userDeatils.getCurrentPassword(), existingUser.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password incorrect");
            }
            if (userDeatils.getNewPassword().equals(userDeatils.getRepeatNewPassword())) {
                existingUser.setPassword(passwordEncoder.encode(userDeatils.getNewPassword()));
            }
        }
        existingUser.setUserType(userDeatils.getUserType());
        System.out.println(existingUser);
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
