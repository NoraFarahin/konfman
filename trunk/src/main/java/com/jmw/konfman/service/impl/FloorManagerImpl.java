package com.jmw.konfman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmw.konfman.dao.BuildingDao;
import com.jmw.konfman.dao.FloorDao;
import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;
import com.jmw.konfman.service.FloorManager;

@Service(value = "floorManager")
public class FloorManagerImpl implements FloorManager {
    @Autowired
    FloorDao dao;
    @Autowired
    BuildingDao bDao;

    public void setFloorDao(FloorDao dao) {
        this.dao = dao;
    }

    public void setBuildingDao(BuildingDao dao) {
        this.bDao = dao;
    }

    public List getFloors() {
        return dao.getFloors();
    }

    public Floor getFloor(String floorId) {
    	return dao.getFloor(Long.valueOf(floorId));
    }

    public void saveFloor(Floor user) {
        dao.saveFloor(user);
    }

    public void removeFloor(String userId) {
        dao.removeFloor(Long.valueOf(userId));
    }

    public List getActiveFloors(){
    	return dao.getActiveFloors();    	
    }

}
