package com.jmw.konfman.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.jmw.konfman.dao.BuildingDao;
import com.jmw.konfman.model.Building;
import com.jmw.konfman.service.BuildingManager;

import java.util.List;

@Service(value = "buildingManager")
public class BuildingManagerImpl implements BuildingManager {
    @Autowired
    BuildingDao dao;

    public void setBuildingDao(BuildingDao dao) {
        this.dao = dao;
    }

    public List getBuildings() {
        return dao.getBuildings();
    }

    public List getActiveBuildings() {
        return dao.getActiveBuildings();
    }
    
    public Building getBuilding(String userId) {
        return dao.getBuilding(Long.valueOf(userId));
    }

    public void saveBuilding(Building user) {
        dao.saveBuilding(user);
    }

    public void removeBuilding(String userId) {
        dao.removeBuilding(Long.valueOf(userId));
    }
    
}
