package com.jmw.konfman.web;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.ui.ModelMap;

import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.service.FloorManager;

public class RoomControllerTest extends MockObjectTestCase {
    private RoomController c = new RoomController();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        mockManager = new Mock(FloorManager.class);
        c.floorManager = (FloorManager) mockManager.proxy();
    }

    public void testGetFloors() throws Exception {
        // set expected behavior on manager
        Floor f = new Floor();
        f.setName("ControllerTest");
        
        mockManager.expects(once()).method("getFloor")
                   .will(returnValue(f));

        ModelMap map = new ModelMap();
        String result = c.execute(map, "1");
        assertFalse(map.isEmpty());
        assertNotNull(map.get("floor"));
        assertEquals("roomList", result);
        
        // verify expectations
        mockManager.verify();
    }
}
