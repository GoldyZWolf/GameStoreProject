package com.example.security.full.security.auth.service;

import com.example.security.full.security.users.Requests.UsersRequest;
import com.example.security.full.security.users.model.User;

import java.util.Optional;

public interface AuthService {
    public Optional<User> AddUser(UsersRequest user) throws Exception;
}
