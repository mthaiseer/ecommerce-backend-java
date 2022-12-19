package com.shopme.service;

import com.shopme.entity.User;
import com.shopme.exception.DuplicateEmailException;
import com.shopme.exception.UserNotFoundException;
import com.shopme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final int PAGE_SIZE = 4;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll(){
       return (List<User>) userRepository.findAll();
    }

    public User save(User user) throws Exception {

        if(!isEmailUnique(user.getEmail())){
            throw new Exception("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean isEmailUnique(String email){

        if( email == null || userRepository.findByEmail(email) != null){
            return false;
        }
        return true;

    }

    public User update(Long id, User user) throws UserNotFoundException, DuplicateEmailException {

        Optional<User> existingUserOptional = userRepository.findById(id);
        if(existingUserOptional.isEmpty() || !existingUserOptional.isPresent()){
            throw new UserNotFoundException("User does not exists !");
        }

        User existingUser  = existingUserOptional.get();

        if(!existingUser.getEmail().equals(user.getEmail()) && !isEmailUnique(user.getEmail())){
            throw new DuplicateEmailException("Email already exists");
        }

        existingUser =  existingUser.builder()
                .id(existingUser.getId())
                .password(existingUser.getPassword())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles())
                .build();

        return userRepository.save(existingUser);

    }

    public Page<User> findByPage(int pageNumber){

        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        return userRepository.findAll(pageable);


    }
}
