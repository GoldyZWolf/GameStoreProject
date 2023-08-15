package com.example.security.full.security.games.service;
import com.example.security.full.security.games.Requests.GameRequest;
import com.example.security.full.security.games.model.Game;
import com.example.security.full.security.games.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public List<Game> GetAllGames() {
        return gameRepository.findAll();
    }

    public Game GetGameById(Long id) {
        return gameRepository.findById(id).orElseThrow();
    }
    


    public Game AddGame(GameRequest game) {
        Game newGame = new Game();
        
        newGame.setGameTitle(game.getGameTitle());
        newGame.setGameDetail(game.getGameDetail());
        newGame.setPrice(game.getPrice());
        newGame.setGameGenre(game.getGameGenre());
        return gameRepository.save(newGame);
    }
}
