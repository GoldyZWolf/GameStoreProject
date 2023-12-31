package com.example.security.full.security.users.service;

import com.example.security.full.security.users.Requests.UsersRequest;
import com.example.security.full.security.users.model.User;
import com.example.security.full.security.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    public List<User> GetAllUsers() {
        return usersRepository.findAll();
    }

    public User AddUser(UsersRequest user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setRoles(user.getRoles());
        return usersRepository.save(newUser);
    }
}
