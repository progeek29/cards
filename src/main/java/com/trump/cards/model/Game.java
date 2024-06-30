package com.trump.cards.model;
import lombok.*;

import java.util.List;

//@Data
//@Getter
//@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Game {

    public Game(List<Player> players, List<Card> centerCards, List<Card> deck, String trumpSuit, int gameNumber, int[] pairsWon, int currentPlayerIndex) {
        this.players = players;
        this.centerCards = centerCards;
        this.deck = deck;
        this.trumpSuit = trumpSuit;
        this.gameNumber = gameNumber;
        this.pairsWon = pairsWon;
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Card> getCenterCards() {
        return centerCards;
    }

    public void setCenterCards(List<Card> centerCards) {
        this.centerCards = centerCards;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public String getTrumpSuit() {
        return trumpSuit;
    }

    public void setTrumpSuit(String trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public int[] getPairsWon() {
        return pairsWon;
    }

    public void setPairsWon(int[] pairsWon) {
        this.pairsWon = pairsWon;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    private List<Player> players;
    private List<Card> centerCards;
    private List<Card> deck;
    private String trumpSuit;
    private int gameNumber;
    @Getter(AccessLevel.PUBLIC)
    private int[] pairsWon;
    private int currentPlayerIndex;
    private List<ChatMessage> chatMessages;


    // Method to check if a team has won
    public boolean hasTeamWon(int teamIndex) {
        return pairsWon[teamIndex] >= 7; // 7 pairs needed to win
    }

}
