package com.jmw.konfman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmw.konfman.dao.RoomDao;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.service.RoomManager;

@Service(value = "roomManager")
public class RoomManagerImpl implements RoomManager {
    @Autowired
    RoomDao dao;

    public void setRoomDao(RoomDao dao) {
        this.dao = dao;
    }

    public List getRooms() {
        return dao.getRooms();
    }

    public Room getRoom(String roomId) {
    	return dao.getRoom(Long.valueOf(roomId));
    }

    public void saveRoom(Room user) {
        dao.saveRoom(user);
    }

    public void removeRoom(String userId) {
        dao.removeRoom(Long.valueOf(userId));
    }

    public List getActiveRooms(){
    	return dao.getActiveRooms();    	
    }

}
