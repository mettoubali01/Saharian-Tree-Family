package com.example.Controllers;

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
        return userService.addAdmin(user);
    }

    @PutMapping(value = "/admin/update")
    public User updateAdmin(@RequestBody User user) {
        return userService.updateAdmin(user);
    }

    @GetMapping(value = "/admin/{id}")
    public User searchAdminById(@PathVariable int id) {
        return userService.searchAdminById(id);
    }

    @GetMapping(value = "/admin")
    public User searchAdminByEmail(@RequestParam String email) {
        return userService.searchAdminByEmail(email);
    }

    @GetMapping(value = "/admins")
    public List<User> getAdmins() {
        return userService.getAdmins();
    }

    @DeleteMapping(value = "admin/rm")
    public void removeAdminByEmail(@RequestBody User user) {
        userService.removeAdmin(user);
    }

    @DeleteMapping(value = "admin/remove/{id}")
    public void removeAdminById(@PathVariable int id) {
         userService.removeAdminById(id);
    }

    @DeleteMapping(value = "admin/remove")
    public void removeAdminByEmail(@RequestParam String email) {
        userService.removeAdminByEmail(email);
    }
}
