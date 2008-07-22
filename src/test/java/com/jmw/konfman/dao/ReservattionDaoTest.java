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
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("2:00 PM");
        	res.setEndTime("2:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
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
    	res.setDate("07/20/2008");
        try {
        	res.setStartTime("2:00 PM");
        	res.setEndTime("2:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(resDao.saveReservation(res));
        assertTrue("primary key assigned", res.getId() != null);

        assertEquals(count + 1, resDao.getReservations().size());
        Reservation r = (Reservation) resDao.getReservations().get(count);
        assertEquals("comment", r.getComment());
    }

    public void testSaveReservationConflict() throws Exception {
    	config();
    	int count = resDao.getReservations().size();
        Reservation r1 = new Reservation();
        r1.setComment("comment20");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("1:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);

        res = new Reservation();
        res.setComment("comment");
        res.setRoom(room);
    	res.setDate("07/20/2008");
        try {
        	res.setStartTime("2:00 PM");
        	res.setEndTime("2:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertFalse(resDao.saveReservation(res));
    }

    public void testRemoveReservation() throws Exception {
    	config();
        res = new Reservation();
        res.setComment("comment");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("2:00 PM");
        	res.setEndTime("2:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
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
    
    /**
     * Test for a conflict where the test reservation starts after the conflict starts 
     * and ends after the conflict ends
     */
    public void testIsConflictInside(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("1:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        
                
        res = new Reservation();
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");
    	assertNull(res.getId());
        try {
        	res.setStartTime("2:00 PM");
        	res.setEndTime("2:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertTrue(resDao.isConflict(res));

    	//try to make it succeed
    	res = new Reservation();
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("3:30 PM");
        	res.setEndTime("4:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertFalse(resDao.isConflict(res));
    }

    /**
     * Test for a conflict where the test reservation starts during the conflict reservation 
     * but ends after the conflict reservation
     */
    public void testIsConflictAfter(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("1:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        
        res = new Reservation();
        res.setComment("comment10011");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("2:00 PM");
        	res.setEndTime("4:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertTrue(resDao.isConflict(res));
    }

    /**
     * Test for a conflict where the test reservation starts before the conflict starts but 
     * ends in the middle of the conflict reservation
     */
    public void testIsConflictBefore(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("1:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        
        res = new Reservation();
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        res = new Reservation();
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("1:00 PM");
        	res.setEndTime("2:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertTrue(resDao.isConflict(res));
    }

    /**
     * Test for a conflict where the test reservation ends right before the potential conflict 
     */
	public void testIsConflicEdgeBefore(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("1:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
	    //try to make it succeed
		res = new Reservation();
	    res.setComment("comment100");
	    res.setRoom(room);
		res.setDate("07/20/2008");
	
	    try {
	    	res.setStartTime("12:30 PM");
	    	res.setEndTime("1:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertFalse(resDao.isConflict(res));
	}

    /**
     * Test for a conflict where the test reservation starts right after the potential conflict 
     */
	public void testIsConflicEdgeAfter(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("1:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
	    //try to make it succeed
		res = new Reservation();
	    res.setComment("comment100");
	    res.setRoom(room);
		res.setDate("07/20/2008");
	
	    try {
	    	res.setStartTime("3:30 PM");
	    	res.setEndTime("4:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertFalse(resDao.isConflict(res));
	}
	
    /**
     * Test for a conflict where the test reservation starts right after the potential conflict 
     */
	public void testIsConflicEndOnAfterEdge(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("1:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
	    //try to make it succeed
		res = new Reservation();
	    res.setComment("comment100");
	    res.setRoom(room);
		res.setDate("07/20/2008");
	
	    try {
	    	res.setStartTime("2:30 PM");
	    	res.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(resDao.isConflict(res));
	}
	
	/**
     * Test  for a conflict where the test reservation starts before and ends after the conflict 
     */
	public void testIsConflictOutside(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("2:30 PM");
        	r1.setEndTime("3:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        
        res = new Reservation();
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        res = new Reservation();
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("1:00 PM");
        	res.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertTrue("these params should create a conflict", resDao.isConflict(res));
    }

	
    /**
     * Test for a conflict with self 
     */
	public void testIsConflictSelf(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("2:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());
        
        //Retrieve the same reservation and see that it does not conflict with self
        res = resDao.getReservation(r1.getId());
        assertEquals(r1.getId(), res.getId());
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("3:00 PM");
        	res.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertFalse("Self test: These params should NOT create a conflict", resDao.isConflict(res));
    }

    /**
     * Test for a conflict with self 
     */
	public void testIsConflictSelfOutside(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("2:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());
        
        //Retrieve the same reservation and see that it does not conflict with self
        res = resDao.getReservation(r1.getId());
        assertEquals(r1.getId(), res.getId());
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("2:00 PM");
        	res.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertFalse("Self test: These params should NOT create a conflict", resDao.isConflict(res));
    }

    /**
     * Test for two conflicts
     */
	public void testIsConflictTwoConflicts(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("2:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());
        
        r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("3:30 PM");
        	r1.setEndTime("4:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());

        //Retrieve the same reservation and see that it does not conflict with self
        res = resDao.getReservation(r1.getId());
        assertEquals(r1.getId(), res.getId());
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("3:00 PM");
        	res.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertTrue("Two Conflicts: These params should create a conflict", resDao.isConflict(res));
    }

    /**
     * Test for two conflicts
     */
	public void testIsConflictTwoConflictsOutside(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("2:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());
        
        r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("3:30 PM");
        	r1.setEndTime("4:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());

        //Retrieve the same reservation and see that it does not conflict with self
        res = resDao.getReservation(r1.getId());
        assertEquals(r1.getId(), res.getId());
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("2:00 PM");
        	res.setEndTime("5:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertTrue("Two Conflicts outside: These params should create a conflict", resDao.isConflict(res));
    }

    /**
     * Test for two conflicts
     */
	public void testIsConflictOutsideTwoSelf(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("2:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());
        
        r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("3:30 PM");
        	r1.setEndTime("4:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());

        //Retrieve the same reservation and see that it does not conflict with self
        res = resDao.getReservation(r1.getId());
        assertEquals(r1.getId(), res.getId());
        res.setComment("comment100");
        res.setRoom(room);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("3:00 PM");
        	res.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertTrue("Two Conflicts: These params should create a conflict", resDao.isConflict(res));
    }

    /**
     * Test if there is a conflict if two rooms are selected
     */
	public void testIsConflictTwoRooms(){
    	config();
    	Room room2 = new Room();
        room2 = new Room();
        room2.setFloor(floor);
        rDao.saveRoom(room2);

        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("2:30 PM");
        	r1.setEndTime("3:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        assertNotNull(r1.getId());
        
        //Retrieve the same reservation and see that it does not conflict with self
        res = resDao.getReservation(r1.getId());
        assertEquals(r1.getId(), res.getId());
        res.setComment("comment100");
        res.setRoom(room2);
    	res.setDate("07/20/2008");

        try {
        	res.setStartTime("3:00 PM");
        	res.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
    	assertFalse("These params should NOT create a conflict", resDao.isConflict(res));
    }

    /**
     * Test for a conflict where the test reservation starts right after the potential conflict 
     */
	public void testIsConflicOnBothEnds(){
    	config();
        Reservation r1 = new Reservation();
        r1.setComment("comment10");
        r1.setRoom(room);
        r1.setDate("07/20/2008");
        try {
        	r1.setStartTime("2:00 PM");
        	r1.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        resDao.saveReservation(r1);
        Reservation r2 = new Reservation();
        r2.setComment("comment10");
        r2.setRoom(room);
        r2.setDate("07/20/2008");
        try {
        	r2.setStartTime("4:00 PM");
        	r2.setEndTime("5:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r2));
	    //try to make it succeed
		res = new Reservation();
	    res.setComment("comment100");
	    res.setRoom(room);
		res.setDate("07/20/2008");
	
	    try {
	    	res.setStartTime("2:30 PM");
	    	res.setEndTime("5:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(resDao.isConflict(res));
	}
}
