package com.recorda.admin.users.resource;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.model.User;
import com.recorda.admin.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for users management endpoints
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping
    public User create(@RequestBody User user) {

        try {
            return userService.add(user);
        } catch (BusinessException ue) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ue.getMessage(), ue);
        }
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    public User findById(@PathVariable("id") String id) throws BusinessException {
        return userService.findById(id);
    }


    @ResponseBody
    @PatchMapping(path = "/{id}")
    public User updateName(@PathVariable("id") String id,@RequestBody HashMap<String, String> fields) throws BusinessException {
        return userService.partialUpdate(id, fields);
    }

    @GetMapping(path = "/all")
    public List<User> findAll() throws BusinessException {
        return userService.findAll();
    }

    @GetMapping(path = "/byEmail/{email}")
    public List<User> findByEmail(@PathVariable("email") String email) throws BusinessException {
        return userService.findByEmail(email);
    }


    @GetMapping(path = "/byName/{name}")
    public List<User> findByName(@PathVariable("name") String name) throws BusinessException {
        return userService.findByName(name);
    }
}
