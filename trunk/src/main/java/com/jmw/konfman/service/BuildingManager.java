package com.jmw.konfman.service;

import com.jmw.konfman.model.Building;

import java.util.List;

public interface BuildingManager {
    public List getBuildings();
    public List getActiveBuildings();
    public Building getBuilding(String buildingId);
    public void saveBuilding(Building building);
    public void removeBuilding(String buildingId);
}
