package com.jmw.konfman.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;

public class FloorDaoTest extends BaseDaoTestCase {
    private Floor floor = null;
    private FloorDao dao = null;
    private BuildingDao bDao = null;
    private Building building = null;
    
    public void setFloorDao(FloorDao userDao) {
        this.dao = userDao;
    }

    public void setBuildingDao(BuildingDao bDao) {
        this.bDao = bDao;
    }
    
    public void testGetFloors() {
        building = new Building();
        building.setName("B1");
        bDao.saveBuilding(building);

        floor = new Floor();
        floor.setName("name");
        floor.setTitle("title");
        floor.setBuilding(building);

        dao.saveFloor(floor);

        assertTrue(dao.getFloors().size() >= 1);
    }

    public void testSaveFloor() throws Exception {
        building = new Building();
        building.setName("B1");
        bDao.saveBuilding(building);

        floor = new Floor();
        floor.setName("Rod");
        floor.setTitle("Johnson");
        floor.setBuilding(building);
        
        dao.saveFloor(floor);
        assertTrue("primary key assigned", floor.getId() != null);

        assertNotNull(floor.getName());
    }

    public void testAddAndRemoveFloor() throws Exception {
        building = new Building();
        building.setName("B1");
        bDao.saveBuilding(building);

        floor = new Floor();
        floor.setName("Bill");
        floor.setTitle("Joy");
        floor.setBuilding(building);
        
        dao.saveFloor(floor);

        assertNotNull(floor.getId());
        assertTrue(floor.getName().equals("Bill"));

        log.debug("removing floor...");

        dao.removeFloor(floor.getId());
        endTransaction();

        try {
            floor = dao.getFloor(floor.getId());
            fail("Floor found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
    
    public void testGetBuilding() throws Exception{
        building = new Building();
        building.setName("B1");
        bDao.saveBuilding(building);

        floor = new Floor();
        floor.setName("Building-get");
        floor.setTitle("Joy");
        floor.setBuilding(building);
        
        dao.saveFloor(floor);
        System.out.println("Saved floor");
        Floor f = dao.getFloor(floor.getId());
        Building b = f.getBuilding();
        assertNotNull(b);
        assertEquals(building.getId(), b.getId());
    	System.out.println("Got building");
    }
    
    public void testGetActiveFloors() throws Exception {
        building = new Building();
        building.setName("B1");
        bDao.saveBuilding(building);

        floor = new Floor();
        floor.setName("Bill");
        floor.setTitle("Joy");
        floor.setBuilding(building);
        floor.setActive(true);
        
        int currentFloorSize = dao.getActiveFloors().size();

        dao.saveFloor(floor);
        assertNotNull(floor.getId());
        assertTrue(floor.getName().equals("Bill"));

        List floors = dao.getActiveFloors();
        assertEquals(currentFloorSize + 1, floors.size());

    	Floor floor2 = new Floor();
        floor2.setName("Bill2");
        floor2.setTitle("Joy");
        floor2.setBuilding(building);
        floor2.setActive(false);

        dao.saveFloor(floor2);
        assertNotNull(floor2.getId());
        assertTrue(floor2.getName().equals("Bill2"));
        
        floors = dao.getActiveFloors();
        assertEquals(currentFloorSize + 1, floors.size());
    }
}
