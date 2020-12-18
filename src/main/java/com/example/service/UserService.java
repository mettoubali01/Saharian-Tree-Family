package com.example.service;

import com.example.beans.User;
import com.example.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public User updateUser(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public User addUser(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return iUserRepository.findAll();
    }

    @Override
    public User searchUserById(int id) {
        System.out.println("EEEEEEEEEEEEEEEEEEEEEEE " + iUserRepository.findById(id).get());
        return iUserRepository.findById(id).get();
    }

    @Override
    public User searchUserByEmail(String email) {
        return iUserRepository.findUserByEmail(email);
    }

    @Override
    public void removeUser(User user) {
        iUserRepository.delete(user);
    }

    @Override
    public void removeUserByEmail(String email) {
        iUserRepository.deleteUserByEmail(email);
    }

    @Override
    public void removeUserById(int id) {
        iUserRepository.deleteById(id);
    }
}
