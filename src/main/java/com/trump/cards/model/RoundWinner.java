package com.trump.cards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundWinner {

    private Game game;
    private List<Card> roundCards;
    private String leadSuit;
    private String trumpSuit;


}
