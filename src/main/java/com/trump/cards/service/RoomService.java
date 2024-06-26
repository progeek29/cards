package com.trump.cards.service;

import com.trump.cards.model.Game;
import com.trump.cards.model.Player;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class RoomService {

    private String roomId;
    private List<Player> players;
    private Game game;

//    private final ConcurrentMap<String, Game> rooms = new ConcurrentHashMap<>();

//    public String createRoom(Game game) {
//        String roomKey = generateUniqueKey();
//        rooms.put(roomKey, game);
//        return roomKey;
//    }
//
//    public Game getRoom(String roomKey) {
//        return rooms.get(roomKey);
//    }
//
//    public void removeRoom(String roomId) {
//        rooms.remove(roomId);
//    }
//
//    private String generateUniqueKey() {
//        // Generate a unique key for the room
////        return UUID.randomUUID().toString();
//        return Long.toHexString(Double.doubleToLongBits(Math.random()));
//    }

//    public Collection<Game> getAllRooms() {
//        return rooms.values();
//    }
}

