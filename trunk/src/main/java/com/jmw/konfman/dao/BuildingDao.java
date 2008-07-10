package com.jmw.konfman.dao;

import com.jmw.konfman.model.Building;

import java.util.List;


public interface BuildingDao extends Dao {
    public List getBuildings();
    public List getActiveBuildings();
    public Building getBuilding(Long buildingId);
    public void saveBuilding(Building building);
    public void removeBuilding(Long buildingId);
}
