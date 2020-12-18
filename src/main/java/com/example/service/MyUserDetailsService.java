package com.example.service;


import com.example.beans.User;
import com.example.beans.MyUserDetails;
import com.example.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements  UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email ) throws UsernameNotFoundException {
        User user = iUserRepository.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find the user");
        }
        return new MyUserDetails(user);
    }
}
