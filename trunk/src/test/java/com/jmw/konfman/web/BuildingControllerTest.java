package com.jmw.konfman.web;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.ui.ModelMap;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.service.BuildingManager;
import com.jmw.konfman.web.BuildingController;

public class BuildingControllerTest extends MockObjectTestCase {
    private BuildingController c = new BuildingController();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        mockManager = new Mock(BuildingManager.class);
        c.buildingManager = (BuildingManager) mockManager.proxy();
    }

    public void testGetBuildings() throws Exception {
        // set expected behavior on manager
        Building building1 = new Building();
        building1.setName("ControllerTest");
        List<Building> buildings = new ArrayList<Building>();
        buildings.add(building1);
        
        mockManager.expects(once()).method("getBuildings")
                   .will(returnValue(buildings));

        ModelMap map = new ModelMap();
        String result = c.execute(map);
        assertFalse(map.isEmpty());
        assertNotNull(map.get("buildingList"));
        assertEquals("buildingList", result);
        
        // verify expectations
        mockManager.verify();
    }
}
