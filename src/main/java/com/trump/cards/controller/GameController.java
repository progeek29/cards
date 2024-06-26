package com.trump.cards.controller;

import com.trump.cards.model.ChatMessage;
import com.trump.cards.model.Game;
import com.trump.cards.model.Room;
import com.trump.cards.model.RoundWinner;
import com.trump.cards.service.GameService;
import com.trump.cards.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/start")
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

//    @PostMapping("/room/create")
//    public ResponseEntity<String> createRoom(@RequestBody String roomId) {
//        roomService.createRoom(roomId);
//        return ResponseEntity.ok("Room created: " + roomId);
//    }
//
//    @MessageMapping("/room/{roomId}/start")
//    public void startGame(@DestinationVariable String roomId) {
//        Room room = roomService.getRoom(roomId);
//        if (room != null) {
//            gameService.startGame(room);
//            // Notify all players in the room that the game has started
//            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/game-started", "Game started!");
//        }
//    }
//
//    @GetMapping("/room/{roomKey}")
//    public Game getRoom(@PathVariable String roomKey) {
//        return roomService.getRoom(roomKey);
//    }
//
//    // WebSocket endpoints
//    @MessageMapping("/play")
//    @SendTo("/topic/game")
//    public Game play(Game game) {
//        // Game logic here
//        return game;
//    }
//
//    @MessageMapping("/chat")
//    @SendTo("/topic/chat")
//    public ChatMessage chat(ChatMessage message) {
//        // Handle chat message
//        return message;
//    }


}
