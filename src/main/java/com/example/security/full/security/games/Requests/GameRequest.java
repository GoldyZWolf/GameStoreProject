package com.example.security.full.security.games.Requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameRequest {
    private String gameTitle;
    private String gameDetail;
    private String gameGenre;
    private Double price;
}
