package com.jmw.konfman.web;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.ui.ModelMap;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.service.BuildingManager;

public class FloorControllerTest extends MockObjectTestCase {
    private FloorController c = new FloorController();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        mockManager = new Mock(BuildingManager.class);
        c.buildingManager= (BuildingManager) mockManager.proxy();
    }

    public void testGetFloors() throws Exception {
        // set expected behavior on manager
        Building b = new Building();
        b.setName("ControllerTest");
        
        mockManager.expects(once()).method("getBuilding")
                   .will(returnValue(b));

        ModelMap map = new ModelMap();
        String result = c.execute(map, "1");
        assertFalse(map.isEmpty());
        assertNotNull(map.get("building"));
        assertEquals("appadmin/floorList", result);
        
        // verify expectations
        mockManager.verify();
    }
}
