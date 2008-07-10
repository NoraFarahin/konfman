package com.jmw.konfman.web;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.ui.ModelMap;

import com.jmw.konfman.model.Floor;
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
        Floor floor1 = new Floor();
        floor1.setName("ControllerTest");
        List<Floor> floors = new ArrayList<Floor>();
        floors.add(floor1);
        
        mockManager.expects(once()).method("getFloors")
                   .will(returnValue(floors));

        ModelMap map = new ModelMap();
        String result = c.execute(map, "1");
        assertFalse(map.isEmpty());
        assertNotNull(map.get("floorList"));
        assertEquals("floorList", result);
        
        // verify expectations
        mockManager.verify();
    }
}
