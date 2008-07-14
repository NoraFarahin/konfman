package com.jmw.konfman.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Room;

public class RoomDaoTest extends BaseDaoTestCase {
    private Room room = null;
    private RoomDao dao = null;
    private BuildingDao bDao = null;
    private Building building = null;
    private FloorDao fDao = null;
    private Floor floor = null;
    
    public void setRoomDao(RoomDao roomDao) {
        this.dao = roomDao;
    }

    public void setBuildingDao(BuildingDao bDao) {
        this.bDao = bDao;
    }
    
    public void setFloorDao(FloorDao fDao) {
        this.fDao = fDao;
    }

    private void config(){
        building = new Building();
        building.setName("B1");
        bDao.saveBuilding(building);
        floor = new Floor();
        floor.setName("f1");
        floor.setBuilding(building);
        fDao.saveFloor(floor);
    }
    
    public void testGetRooms() {
    	config();
        room = new Room();
        room.setName("name");
        room.setTitle("title");
        room.setFloor(floor);

        dao.saveRoom(room);

        assertTrue(dao.getRooms().size() >= 1);
    }

    public void testSaveRoom() throws Exception {
    	config();
        room = new Room();
        room.setName("Rod");
        room.setTitle("Johnson");
        room.setFloor(floor);
        
        dao.saveRoom(room);
        assertTrue("primary key assigned", room.getId() != null);

        assertNotNull(room.getName());
    }

    public void testAddAndRemoveRoom() throws Exception {
    	config();
    	room = new Room();
        room.setName("Bill");
        room.setTitle("Joy");
        room.setFloor(floor);
        
        dao.saveRoom(room);

        assertNotNull(room.getId());
        assertTrue(room.getName().equals("Bill"));

        log.debug("removing room...");

        dao.removeRoom(room.getId());
        endTransaction();

        try {
            room = dao.getRoom(room.getId());
            fail("Room found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
    
    public void testGetFloor() throws Exception{
    	config();
        room = new Room();
        room.setName("Building-get");
        room.setTitle("Joy");
        room.setFloor(floor);
        
        dao.saveRoom(room);
        System.out.println("Saved room");
        Room r = dao.getRoom(room.getId());
        Floor f = r.getFloor();
        assertNotNull(f);
        assertEquals(floor.getId(), f.getId());
    }
    
    public void testGetActiveRooms() throws Exception {
    	config();
    	room = new Room();
        room.setName("Bill");
        room.setTitle("Joy");
        room.setFloor(floor);
        room.setActive(true);
        
        int currentRoomCount = dao.getActiveRooms().size();
        
        dao.saveRoom(room);
        assertNotNull(room.getId());
        assertTrue(room.getName().equals("Bill"));

        List rooms = dao.getActiveRooms();
    	assertEquals(currentRoomCount + 1, rooms.size());

    	Room room2 = new Room();
        room2.setName("Bill2");
        room2.setTitle("Joy");
        room.setFloor(floor);
        room2.setActive(false);

        dao.saveRoom(room2);
        assertNotNull(room2.getId());
        assertTrue(room2.getName().equals("Bill2"));
        
        rooms = dao.getActiveRooms();
    	assertEquals(currentRoomCount + 1, rooms.size());
    }
}
