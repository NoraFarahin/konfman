package com.jmw.konfman.dao;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;

public class BuildingDaoTest extends BaseDaoTestCase {
    private Building building = null;
    private BuildingDao dao = null;
    private FloorDao fDao = null;

    public void setBuildingDao(BuildingDao userDao) {
        this.dao = userDao;
    }

    public void setFloorDao(FloorDao fDao) {
        this.fDao = fDao;
    }

    public void testGetBuildings() {
    	int currentBuildingCount = dao.getBuildings().size();
    	
        building = new Building();
        building.setName("name");
        building.setTitle("title");

        dao.saveBuilding(building);

        assertEquals(currentBuildingCount + 1, dao.getBuildings().size());
    }

    public void testGetActiveBuildings() {
    	//see how many active buildings there are now 
    	int currentBuildingCount = dao.getActiveBuildings().size();
    	
        building = new Building();
        building.setName("name");
        building.setTitle("title");

        dao.saveBuilding(building);

        //the number should be the same since we have only added an inactive building
        assertEquals(currentBuildingCount, dao.getActiveBuildings().size());

        Building b1 = new Building();
        b1.setName("name1");
        b1.setTitle("title1");
        b1.setActive(true);
        dao.saveBuilding(b1);
        
        //add an active building and confirm that the count has increased by 1

        assertEquals(currentBuildingCount + 1, dao.getActiveBuildings().size());
}

    public void testSaveBuilding() throws Exception {
        building = new Building();
        building.setName("Rod");
        building.setTitle("Johnson");

        dao.saveBuilding(building);
        assertTrue("primary key assigned", building.getId() != null);

        assertNotNull(building.getName());
    }

    public void testAddAndRemoveBuilding() throws Exception {
    	int currentBuildingCount = dao.getBuildings().size();

    	building = new Building();
        building.setName("Bill");
        building.setTitle("Joy");

        dao.saveBuilding(building);
        assertEquals(currentBuildingCount + 1, dao.getBuildings().size());
        assertNotNull(building.getId());
        assertTrue(building.getName().equals("Bill"));

        log.debug("removing building...");
        dao.removeBuilding(building.getId());
        //should be back where we started
        assertEquals(currentBuildingCount, dao.getBuildings().size());

        try {
            building = dao.getBuilding(building.getId());
            fail("Building found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
    
    /*
    public void testGetBuildingFloors() throws Exception{
        building = new Building();
        building.setName("b1");
        building.setTitle("Joy");

        dao.saveBuilding(building);

        assertNotNull(building.getId());
        assertTrue(building.getName().equals("b1"));
        
        log.debug("building saved");
        
        Floor floor = new Floor();
        floor.setName("f1");
        floor.setBuilding(building);
        
        int currentFloorCount = fDao.getFloors().size();
        fDao.saveFloor(floor);
    	
        assertNotNull(floor.getId());
        assertTrue(floor.getName().equals("f1"));
        assertEquals(currentFloorCount + 1, fDao.getFloors().size());
        
        log.debug("Floor saved: " + floor.getId() + " Building: " + floor.getBuilding().getId());
        //try reloading building
        Building b2 = floor.getBuilding(); //dao.getBuilding(building.getId());
        assertNotNull(b2);
        log.debug("Attempted to retreive building");
        List<Floor> floors = b2.getFloors();
        assertNotNull(floors);
        assertEquals(1, floors.size());
    } */
}
