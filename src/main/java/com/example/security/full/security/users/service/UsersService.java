package com.example.security.full.security.users.service;

import com.example.security.full.security.users.Requests.UsersRequest;
import com.example.security.full.security.users.model.User;

import java.util.List;

public interface UsersService {
    public List<User> GetAllUsers();

    public User AddUser(UsersRequest user);
}
