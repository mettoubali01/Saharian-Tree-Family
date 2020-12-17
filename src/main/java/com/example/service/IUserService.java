package com.example.service;

import com.example.beans.User;

import java.util.List;

public interface IUserService {
    User addAdmin(User user);
    User updateAdmin(User user);
    List<User> getAdmins();
    User searchAdminById(int id);
    User searchAdminByEmail(String email);
    void removeAdminByEmail(String email);
    void removeAdminById(int id );
    void removeAdmin(User user);
}
