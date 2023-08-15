package com.example.security.full.security.users.Requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersRequest {
    private String username;
    private String email;
    private String password;
    private String roles;
}
