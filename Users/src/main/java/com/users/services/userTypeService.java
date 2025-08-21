package com.users.services;

import com.users.models.userTypeModel;
import com.users.repositories.userTypeRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class userTypeService {

    @Autowired
    userTypeRepository userTypeRepository;

    @Transactional(readOnly = true)
    public List<userTypeModel> getAllUserTypes() {
        if (userTypeRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No users found");
        }
        return userTypeRepository.findAll();
    }

    @Transactional
    public userTypeModel createUserType(userTypeModel userTypeModel) {
        return userTypeRepository.save(userTypeModel);
    }

    @Transactional
    public userTypeModel updateUserType(Long id, userTypeModel userTypeModelU) {
        userTypeModel ut = userTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User type not found"));
        ut.setTypeName(userTypeModelU.getTypeName());
        return userTypeRepository.save(ut);
    }

    @Transactional
    public String deleteUserType(Long id) {
        if (!userTypeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User type not found");
        }
        userTypeRepository.deleteById(id);
        return "User type deleted successfully";
    }

}
