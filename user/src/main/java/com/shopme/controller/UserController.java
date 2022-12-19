package com.shopme.controller;

import com.shopme.entity.User;
import com.shopme.exception.DuplicateEmailException;
import com.shopme.exception.UserNotFoundException;
import com.shopme.request.UserCreateRequest;
import com.shopme.request.UserUpdateRequest;
import com.shopme.service.UserCSVExporter;
import com.shopme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping
    public User create(@RequestBody UserCreateRequest userRequest) throws Exception {
        //TODO return response object
        return userService.save(userRequest.to());
    }

    @PutMapping("/{id}")
    public User update(@PathVariable(name ="id") Long id , @RequestBody UserUpdateRequest userRequest)
            throws UserNotFoundException,
            DuplicateEmailException {
        return userService.update(id, userRequest.to());
    }

    @GetMapping("/page/{pageNumber}")
    public List<User> findByPage(@PathVariable(name = "pageNumber") int pageNumber){
        return userService.findByPage(pageNumber).getContent();
    }

    @GetMapping("/export/csv")
    public void exportCSV(HttpServletResponse httpResponse) throws IOException {

        List<User> users = userService.findAll();
        UserCSVExporter csvExporter = new UserCSVExporter();
        csvExporter.export(httpResponse, users);


    }


    //TODO findById
    //TODO delete user
    //TODO enable status (userId)
    //TODO sorting, searching and filtering data
    //TODO Excel write poi library
    //TODO PDF write


}
