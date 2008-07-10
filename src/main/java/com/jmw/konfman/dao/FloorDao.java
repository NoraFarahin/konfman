package com.jmw.konfman.dao;

import java.util.List;

import com.jmw.konfman.model.Floor;


public interface FloorDao extends Dao {
    public List getFloors();
    public List getActiveFloors();
    public Floor getFloor(Long floorId);
    public void saveFloor(Floor floor);
    public void removeFloor(Long floorId);
}
