package com.jmw.konfman.service;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Room;

public class RoomManagerTest extends AbstractTransactionalDataSourceSpringContextTests {
    private RoomManager roomManager;
    private BuildingManager buildingManager;
    private Building building = null;
    private FloorManager floorManager;
    private Floor floor = null;

    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
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

    public void setFloorManager(FloorManager floorManager) {
        this.floorManager = floorManager;
        floor = new Floor();
        floor.setName("f1");
        floorManager.saveFloor(floor);
    }

    protected void onSetUpInTransaction() throws Exception {
        deleteFromTables(new String[] {"ROOMS"});
        deleteFromTables(new String[] {"FLOORS"});
    }

    public void testGetRooms() {
        Room room1 = new Room();
        room1.setName("Abbie");
        room1.setTitle("Raible");
        room1.setFloor(floor);
        Room room2 = new Room();
        room2.setName("Jack");
        room2.setTitle("Raible");
        room2.setFloor(floor);
        roomManager.saveRoom(room1);
        roomManager.saveRoom(room2);

        assertEquals(2, roomManager.getRooms().size());
        Floor f = room2.getFloor();
        assertNotNull(f);
    }
}
