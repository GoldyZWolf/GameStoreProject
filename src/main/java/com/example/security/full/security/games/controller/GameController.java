package com.example.security.full.security.games.controller;


import com.example.security.full.security.games.Requests.GameRequest;
import com.example.security.full.security.games.model.Game;
import com.example.security.full.security.games.service.GameService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games")
public class GameController {
    private final GameService gameService;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public List<Game> GetGames() {
        return gameService.GetAllGames();
    }

    @GetMapping("/{id}")
    public Game GetGameById(@PathVariable Long id) {
        return gameService.GetGameById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public Game AddGame(@RequestBody GameRequest game) {
        return gameService.AddGame(game);
    }

    
}
