package com.example.controllers;

import com.example.beans.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/admin/add")
    public User addAdmin(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping(value = "/admin/update")
    public User updateAdmin(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping(value = "/admin/{id}")
    public User searchAdminById(@PathVariable int id) {
        return userService.searchUserById(id);
    }

    @GetMapping(value = "/adminn")
    public User searchAdminByEmail(@RequestParam String email) {
        return userService.searchUserByEmail(email);
    }

    @GetMapping(value = "/admins")
    public List<User> getAdmins() {
        return userService.getUsers();
    }

    @DeleteMapping(value = "admin/rm")
    public void removeAdminByEmail(@RequestBody User user) {
        userService.removeUser(user);
    }

    @DeleteMapping(value = "admin/remove/{id}")
    public void removeAdminById(@PathVariable int id) {
         userService.removeUserById(id);
    }

    @DeleteMapping(value = "admin/remove")
    public void removeAdminByEmail(@RequestParam String email) {
        userService.removeUserByEmail(email);
    }
}
