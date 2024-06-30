package com.trump.cards.controller;

import com.trump.cards.model.*;
import com.trump.cards.service.GameService;
import com.trump.cards.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/game")
public class GameController {

//    @Autowired
//    private GameService gameService;4

    private final GameService gameService;
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameController(GameService gameService, RoomService roomService, SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/startGame")
    public Game startGame() {
        return gameService.initializeGame();
    }


    @PostMapping("/choose-trump")
    public Game chooseTrumpSuit(@RequestParam String trumpSuit, @RequestBody Game game) {
        return gameService.chooseTrumpSuit(game, trumpSuit);
    }

    @PostMapping("/playCard/{playerId}")
    public String playCard(@RequestBody Game game, @PathVariable int playerId) {
        return gameService.playCard(game, playerId);
    }

    @PostMapping("/determineRoundWinner")
    public ResponseEntity<Integer> determineRoundWinner(@RequestBody RoundWinner request) {
        int winningPlayerIndex = gameService.determineRoundWinner(request.getGame(), request.getRoundCards(), request.getLeadSuit(), request.getTrumpSuit());
        return ResponseEntity.ok(winningPlayerIndex);
    }

    @MessageMapping("/sendChat")
    public void sendChatMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/chat", chatMessage);
    }

    @MessageMapping("/joinChat")
    public void joinChatNotification(@Payload ChatMessage chatMessage) {
//        ChatMessage notificationMessage = new ChatMessage(chatMessage.getSender(), "Just joined the chat ");
        ChatMessage notificationMessage = new ChatMessage("Joined the chat ", chatMessage.getSender());
        messagingTemplate.convertAndSend("/topic/chat", notificationMessage);
    }


}
