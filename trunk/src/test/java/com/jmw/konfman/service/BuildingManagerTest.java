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

    public void testGetBuildings() {
        int buildingCount = buildingManager.getBuildings().size();
    	Building building1 = new Building();
        building1.setName("Abbie");
        building1.setTitle("Raible");
        Building building2 = new Building();
        building2.setName("Jack");
        building2.setTitle("Raible");
        buildingManager.saveBuilding(building1);
        buildingManager.saveBuilding(building2);

        assertEquals(buildingCount + 2, buildingManager.getBuildings().size());
        
        buildingManager.removeBuilding(building1.getId().toString());
        assertEquals(buildingCount + 1, buildingManager.getBuildings().size());
                
        buildingManager.removeBuilding(building2.getId().toString());
        assertEquals(buildingCount, buildingManager.getBuildings().size());
        
        try{
            buildingManager.removeBuilding(building2.getId().toString());
            fail("Should not be able to remove an object that was removed already!");
        }catch (Exception e){}
        assertEquals(buildingCount, buildingManager.getBuildings().size());
    }
}
