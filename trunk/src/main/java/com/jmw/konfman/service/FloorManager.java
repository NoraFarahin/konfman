package com.jmw.konfman.service;

import com.jmw.konfman.model.Floor;

import java.util.List;

public interface FloorManager {
    public List getFloors();
    public List getActiveFloors();
    public Floor getFloor(String floorId);
    public void saveFloor(Floor floor);
    public void removeFloor(String floorId);
}
