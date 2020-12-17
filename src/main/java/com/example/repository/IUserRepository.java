package com.example.repository;

import com.example.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    User findAdminByEmail(String email);
    void deleteAdminByEmail(String email);
}
