package com.example.service;

import com.example.beans.User;

import java.util.List;

public interface IUserService {
    User addUser(User user);
    User updateUser(User user);
    List<User> getUsers();
    User searchUserById(int id);
    User searchUserByEmail(String email);
    void removeUserByEmail(String email);
    void removeUserById(int id );
    void removeUser(User user);
}
