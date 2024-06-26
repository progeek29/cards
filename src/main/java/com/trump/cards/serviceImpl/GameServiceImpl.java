package com.trump.cards.serviceImpl;

import com.trump.cards.model.Card;
import com.trump.cards.model.Game;
import com.trump.cards.model.Player;
import com.trump.cards.service.GameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private int gameNumber = 0;

    @Override
    public Game initializeGame() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player(i + 1, new ArrayList<>()));
        }

        List<Card> deck = createDeck();
        Collections.shuffle(deck);

        // Distribute 5 cards to each player
        for (int i = 0; i < 5; i++) {
            for (Player player : players) {
                player.getHand().add(deck.remove(0));
            }
        }

        // Initialize pairs won count for both teams
        int[] pairsWon = {0, 0}; // Team 1 (Player 1 & 3), Team 2 (Player 2 & 4)

        Game game = new Game(players, new ArrayList<>(), deck, null, gameNumber, pairsWon, 0);
        return game;
    }

    private List<Card> createDeck() {
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        List<Card> deck = new ArrayList<>();

        int playerIndex = 0;

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit, playerIndex));
                playerIndex = (playerIndex + 1) % 4;
            }
        }
        return deck;
    }

    @Override
    public Game chooseTrumpSuit(Game game, String trumpSuit) {
        if (trumpSuit == null || trumpSuit.isEmpty()) {
            throw new IllegalArgumentException("Trump suit cannot be null or empty");
        }
        game.setTrumpSuit(trumpSuit);
        distributeRemainingCards(game);
        return game;
    }

    private void distributeRemainingCards(Game game) {
        int currentPlayer = 0;
        while (!game.getDeck().isEmpty()) {
            game.getPlayers().get(currentPlayer).getHand().add(game.getDeck().remove(0));
            currentPlayer = (currentPlayer + 1) % 4;
        }
    }

    @Override
    public String playCard(Game game, int playerId) {
        Player player = game.getPlayers().get(playerId);
        if (player.getHand().isEmpty()) {
            throw new IllegalArgumentException("Player's hand cannot be empty");
        }
        // For simplicity, the player always plays the first card in their hand
        Card card = player.getHand().remove(0);
        return card.getRank() + " of " + card.getSuit();
    }

    @Override
    public int determineRoundWinner(Game game, List<Card> roundCards, String leadSuit, String trumpSuit) {
        if (roundCards.isEmpty()) {
            throw new IllegalArgumentException("Round cards cannot be empty");
        }

        System.out.println("Round Cards: " + roundCards);
        System.out.println("Lead Suit: " + leadSuit);
        System.out.println("Trump Suit: " + trumpSuit);

        int maxRankIndex = -1;
        int winner = -1;
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        boolean trumpPlayed = false;

        for (int i = 0; i < roundCards.size(); i++) {
            Card card = roundCards.get(i);
            String suit = card.getSuit();
            String rank = card.getRank();
            int playerIndex = card.getPlayerIndex();

            System.out.println("Processing card: " + rank + " of " + suit + " (Player " + (playerIndex + 1) + ")");

            int rankIndex = -1;
            try {
                for (int j = 0; j < ranks.length; j++) {
                    if (ranks[j].equals(rank)) {
                        rankIndex = j;
                        break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Error: Invalid rank encountered for card: " + rank + " of " + suit);
                e.printStackTrace();
                continue; // Skip this card and proceed with the next one
            }

            // Check if a trump card is played
            if (suit.equals(trumpSuit)) {
                if (!trumpPlayed || rankIndex > maxRankIndex) {
                    maxRankIndex = rankIndex;
//                    winner = playerIndex;
                    winner = i;
                    trumpPlayed = true;
                }
            } else if (!trumpPlayed && suit.equals(leadSuit)) {
                // Normal comparison if no trump card is played
                if (rankIndex > maxRankIndex) {
                    maxRankIndex = rankIndex;
//                    winner = playerIndex;
                    winner = i;
                }
            }
        }

        // Increment pairs won for the winning team
//        int winningPlayerIndex = winner; // Assume winner index represents the winning player
        int winningPlayerIndex = roundCards.get(winner).getPlayerIndex();
        int teamIndex = winningPlayerIndex % 2 == 0 ? 1 : 0; // Determine team index based on player index
        game.getPairsWon()[teamIndex]++;

        System.out.println("Winner: Player " + (winningPlayerIndex + 1));
        return winningPlayerIndex; // Return index of winning card
    }
}
