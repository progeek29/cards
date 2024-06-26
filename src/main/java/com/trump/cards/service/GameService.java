package com.trump.cards.service;

import com.trump.cards.model.Card;
import com.trump.cards.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GameService {

    Game initializeGame();

    Game chooseTrumpSuit(Game game, String trumpSuit);

    String playCard(Game game, int playerId);

    int determineRoundWinner(Game game, List<Card> roundCards, String leadSuit, String trumpSuit);
}
