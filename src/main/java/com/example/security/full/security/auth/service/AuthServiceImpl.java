package com.example.security.full.security.auth.service;

import com.example.security.full.security.users.Requests.UsersRequest;
import com.example.security.full.security.users.model.User;
import com.example.security.full.security.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UsersRepository usersRepository;

    public Optional<User> AddUser(UsersRequest user) throws Exception {

        Optional<User> userExists = usersRepository.findByEmail(user.getEmail());
        if(userExists != null && !userExists.isEmpty()) {throw new Exception("User exists");}
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setRoles("ROLE_USER");
        return Optional.of(usersRepository.save(newUser));
    }
}
