package com.example.security.full.security.users.controller;

import com.example.security.full.security.users.Requests.UsersRequest;
import com.example.security.full.security.users.model.User;
import com.example.security.full.security.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;

    //@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public List<User> GetUsers() {
        return usersService.GetAllUsers();
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public User AddUser(@RequestBody UsersRequest user) {
        return usersService.AddUser(user);
    }

}
