package com.jmw.konfman.service;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.service.BuildingManager;

public class BuildingManagerTest extends AbstractTransactionalDataSourceSpringContextTests {
    private BuildingManager buildingManager;

    public void setBuildingManager(BuildingManager buildingManager) {
        this.buildingManager = buildingManager;
    }

    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        return new String[] {"classpath*:/WEB-INF/applicationContext*.xml"};
    }

    protected void onSetUpInTransaction() throws Exception {
        deleteFromTables(new String[] {"FLOORS"});
        deleteFromTables(new String[] {"BUILDINGS"});
    }

    public void testGetBuildings() {
        Building building1 = new Building();
        building1.setName("Abbie");
        building1.setTitle("Raible");
        Building building2 = new Building();
        building2.setName("Jack");
        building2.setTitle("Raible");
        buildingManager.saveBuilding(building1);
        buildingManager.saveBuilding(building2);

        assertEquals(2, buildingManager.getBuildings().size());
    }
}
