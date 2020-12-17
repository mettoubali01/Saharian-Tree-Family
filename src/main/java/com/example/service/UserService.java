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
    public User updateAdmin(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public User addAdmin(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public List<User> getAdmins() {
        return iUserRepository.findAll();
    }

    @Override
    public User searchAdminById(int id) {
        System.out.println("EEEEEEEEEEEEEEEEEEEEEEE " + iUserRepository.findById(id).get());
        return iUserRepository.findById(id).get();
    }

    @Override
    public User searchAdminByEmail(String email) {
        return iUserRepository.findAdminByEmail(email);
    }

    @Override
    public void removeAdmin(User user) {
        iUserRepository.delete(user);
    }

    @Override
    public void removeAdminByEmail(String email) {
        iUserRepository.deleteAdminByEmail(email);
    }

    @Override
    public void removeAdminById(int id) {
        iUserRepository.deleteById(id);
    }
}
