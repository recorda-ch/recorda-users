package com.recorda.admin.users.resource;

import com.recorda.admin.users.model.User;
import com.recorda.admin.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return userService.add(user);
    }
    @ResponseBody
    @GetMapping(path = "/{id}")
    public User findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }


    @ResponseBody
    @PatchMapping(path = "/{id}")
    public User updateName(@PathVariable("id") String id,@RequestBody HashMap<String, String> fields) {
        return userService.updateMultiple(id, fields);
    }

    @GetMapping(path = "/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(path = "/byEmail/{email}")
    public List<User> findByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }


    @GetMapping(path = "/byName/{name}")
    public List<User> findByName(@PathVariable("name") String name) {
        return userService.findByName(name);
    }
}
