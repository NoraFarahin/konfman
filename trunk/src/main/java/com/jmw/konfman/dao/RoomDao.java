package com.jmw.konfman.dao;

import java.util.List;

import com.jmw.konfman.model.Room;

public interface RoomDao {
    public List getRooms();
    public List getActiveRooms();
    public Room getRoom(Long roomId);
    public void saveRoom(Room room);
    public void removeRoom(Long roomId);

}
