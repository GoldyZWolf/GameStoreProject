package com.example.security.full.security.games.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity(name = "Game")
@Table(name = "game")
public class Game {
    @Id
    @SequenceGenerator(
            name = "game_seq",
            sequenceName = "game_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "game_seq"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    
    @Column(name = "gameTitle", nullable = false)
    private String gameTitle;

    @Column(name = "gameDetail", nullable = false)
    private String gameDetail;
    
    @Column(name = "gameGenre", nullable = false)
    private String gameGenre;
    
    @Column(name = "price", nullable = false)
    private Double price;
    



}