package com.jmw.konfman.service;

import com.jmw.konfman.model.Room;

import java.util.List;

public interface RoomManager {
    public List getRooms();
    public List getActiveRooms();
    public Room getRoom(String roomId);
    public void saveRoom(Room room);
    public void removeRoom(String roomId);
}
