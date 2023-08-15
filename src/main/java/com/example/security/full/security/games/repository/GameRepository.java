package com.example.security.full.security.games.repository;

import com.example.security.full.security.games.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByGameGenre(String gameGenre);
    Optional<Game> findByGameTitle(String gameTitle);
}
