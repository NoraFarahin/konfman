package com.jmw.conference.entities;

import java.util.List;

public class RoomDAO extends AbstractDAO{

    public Room find(Integer id) {
        return (Room)super.find(Room.class, id);
    }
    
    public List findAll() {
        return super.findAll(Room.class);
    }
}
