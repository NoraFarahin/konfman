package com.jmw.konfman.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;

public class ReservattionDaoTest extends BaseDaoTestCase {
    private Floor floor = null;
    private FloorDao dao = null;
    private BuildingDao bDao = null;
    private Building building = null;
    private RoomDao rDao = null;
    private Room room = null;
    private ReservationDao resDao = null;
    private Reservation res = null;
    
    public void setFloorDao(FloorDao fDao) {
        this.dao = fDao;
    }

    public void setBuildingDao(BuildingDao bDao) {
        this.bDao = bDao;
    }
    
    public void setRoomDao(RoomDao rDao) {
        this.rDao = rDao;
    }
    public void setReservationDao(ReservationDao resDao) {
        this.resDao = resDao;
    }

    private void config(){
        building = new Building();
        building.setName("B1");
        bDao.saveBuilding(building);

        floor = new Floor();
        floor.setName("name");
        floor.setTitle("title");
        floor.setBuilding(building);
        dao.saveFloor(floor);
        
        room = new Room();
        room.setFloor(floor);
        rDao.saveRoom(room);
    }
    
    public void testGetReservations() {
    	config();
    	int count = resDao.getReservations().size();
        res = new Reservation();
        res.setComment("comment");
        res.setRoom(room);
        resDao.saveReservation(res);

        assertEquals(count + 1, resDao.getReservations().size());
        Reservation r = (Reservation) resDao.getReservations().get(count);
        assertEquals("comment", r.getComment());
    }

    public void testSaveReservation() throws Exception {
    	config();
    	int count = resDao.getReservations().size();
        res = new Reservation();
        res.setComment("comment");
        res.setRoom(room);
        resDao.saveReservation(res);
        assertTrue("primary key assigned", res.getId() != null);

        assertEquals(count + 1, resDao.getReservations().size());
        Reservation r = (Reservation) resDao.getReservations().get(count);
        assertEquals("comment", r.getComment());
    }

    public void testRemoveReservation() throws Exception {
    	config();
        res = new Reservation();
        res.setComment("comment");
        res.setRoom(room);
        resDao.saveReservation(res);
        resDao.removeReservation(res.getId());
        //endTransaction();

        try {
            res  = resDao.getReservation(res.getId());
            fail("Deleted Reservation found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
}
