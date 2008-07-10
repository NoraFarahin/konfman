package com.jmw.konfman.service;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;

public class FloorManagerTest extends AbstractTransactionalDataSourceSpringContextTests {
    private FloorManager floorManager;
    private BuildingManager buildingManager;
    private Building building = null;

    public void setFloorManager(FloorManager floorManager) {
        this.floorManager = floorManager;
    }

    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        return new String[] {"classpath*:/WEB-INF/applicationContext*.xml"};
    }

    public void setBuildingManager(BuildingManager buildingManager) {
        this.buildingManager = buildingManager;
        building = new Building();
        building.setName("b1");
        buildingManager.saveBuilding(building);
        
    }

    protected void onSetUpInTransaction() throws Exception {
        deleteFromTables(new String[] {"FLOORS"});
    }

    public void testGetFloors() {
        Floor floor1 = new Floor();
        floor1.setName("Abbie");
        floor1.setTitle("Raible");
        floor1.setBuilding(building);
        Floor floor2 = new Floor();
        floor2.setName("Jack");
        floor2.setTitle("Raible");
        floor2.setBuilding(building);
        floorManager.saveFloor(floor1);
        floorManager.saveFloor(floor2);

        assertEquals(2, floorManager.getFloors().size());
        Building b = floor2.getBuilding();
        assertNotNull(b);
    }
}
