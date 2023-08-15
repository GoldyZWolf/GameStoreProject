package com.example.security.full.security.games.service;

import com.example.security.full.security.games.Requests.GameRequest;
import com.example.security.full.security.games.model.Game;

import java.util.List;

public interface GameService {
    public List<Game> GetAllGames();
    public Game GetGameById(Long id);
    public Game AddGame(GameRequest game);
}
