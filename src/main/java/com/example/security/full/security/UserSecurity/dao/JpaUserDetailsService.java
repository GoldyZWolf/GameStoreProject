package com.example.security.full.security.UserSecurity.dao;

import com.example.security.full.security.UserSecurity.model.UserSecurity;
import com.example.security.full.security.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.contains("@")) {return loadUserByEmail(username);}

        System.out.println("username:"+ username);
        return usersRepository.findByUsername(username).map(UserSecurity::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
    }

    
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        if(!email.contains("@")) {return loadUserByUsername(email);}
        System.out.println("email:"+ email);
        return usersRepository.findByEmail(email).map(UserSecurity::new).orElseThrow(() -> new UsernameNotFoundException("Email Not Found!"));
    }
}
