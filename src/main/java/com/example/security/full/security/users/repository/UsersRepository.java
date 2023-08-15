package com.example.security.full.security.users.repository;

import com.example.security.full.security.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    //Optional<User> findByUsernameOrEmail(String usernameOrEmail);
}
