package com.jmw.konfman.dao;

import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;

public class ReservattionDaoTest extends BaseDaoTestCase {
	private Building building = null;
    private BuildingDao bDao = null;
    
	private Floor floor = null;
    private FloorDao dao = null;
    
    private Room room = null;
    private RoomDao rDao = null;
    
    private User user = null;
    private UserDao uDao = null;
    
    private Reservation res = null;
    private ReservationDao resDao = null;
    
    public void setFloorDao(FloorDao fDao) {
        this.dao = fDao;
    }

    public void setBuildingDao(BuildingDao bDao) {
        this.bDao = bDao;
    }
    
    public void setRoomDao(RoomDao rDao) {
        this.rDao = rDao;
    }
    
    public void setUserDao(UserDao uDao){
    	this.uDao = uDao;
    }
    
    public void setReservationDao(ReservationDao resDao) {
        this.resDao = resDao;
    }

    private void config(){
    	user = new User();
    	user.setFirstName("FN");
    	user.setLastName("LN");
    	uDao.saveUser(user);
    	
    	
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
    	int count = 0;
    	List list = resDao.getReservations();
    	if (list != null){
    		count = list.size();
    	}
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

    /**
     * Saves the same reservation twice as this was causing difficulties
     * @throws Exception
     */
    public void testSaveReservationTwice() throws Exception {
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
		assertTrue(resDao.saveReservation(res));
        assertTrue("primary key assigned", res.getId() != null);

		//now try to save again and see that it works
        assertTrue(resDao.saveReservation(res));
    }

    /**
     * Saves the same reservation data twice, the second time as a new reservation.
     * This was causing difficulties.
     * @throws Exception
     */
    public void testSaveReservationDateTwice() throws Exception {
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
		assertTrue(resDao.saveReservation(res));
        assertTrue("primary key assigned", res.getId() != null);

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
		//now try to save again and see that it fails
        assertFalse(resDao.saveReservation(res));
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
    	assertFalse("Should not conflict any more", resDao.isConflict(res));
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
	
	public void testPastAndCurrentUserReservations(){
		config();
    	user = new User();
    	user.setFirstName("FNx");
    	user.setLastName("LNx");
    	uDao.saveUser(user);
		
        Reservation r1 = new Reservation();
        r1.setComment("comment101");
        r1.setRoom(room);
        r1.setDate("09/20/2009");
		r1.setUser(user);
        try {
        	r1.setStartTime("2:00 PM");
        	r1.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r1));
		List past = resDao.getPastUserReservations(user);
		List curr = resDao.getCurrentUserReservations(user);
		assertEquals(0, past.size());
		assertEquals(1, curr.size());
        
        Reservation r2 = new Reservation();
        r2.setComment("comment102");
        r2.setRoom(room);
        r2.setDate("07/20/2008");
		r2.setUser(user);
        try {
        	r2.setStartTime("4:00 PM");
        	r2.setEndTime("5:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r2));
		past = resDao.getPastUserReservations(user);
		curr = resDao.getCurrentUserReservations(user);
		Reservation reservation = (Reservation)past.iterator().next();
		Reservation reservationC = (Reservation)curr.iterator().next();
		assertEquals(1, past.size());
		assertEquals(1, curr.size());
		assertEquals("comment102", reservation.getComment());
		assertEquals("comment101", reservationC.getComment());

        Reservation r3 = new Reservation();
        r3.setComment("comment103");
        r3.setRoom(room);
        r3.setDate("10/20/2009");
		r3.setUser(user);
        try {
        	r3.setStartTime("2:00 PM");
        	r3.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r3));
		past = resDao.getPastUserReservations(user);
		curr = resDao.getCurrentUserReservations(user);
		assertEquals(1, past.size());
		assertEquals(2, curr.size());

		Iterator iter = curr.iterator();
		reservation = (Reservation)past.iterator().next();
		reservationC = (Reservation)iter.next();
		assertEquals("comment102", reservation.getComment());
		assertEquals("comment101", reservationC.getComment());
		reservationC = (Reservation)iter.next();
		assertEquals("comment103", reservationC.getComment());
	}

	public void testPastAndCurrentRoomReservations(){
		config();
    	user = new User();
    	user.setFirstName("FNx");
    	user.setLastName("LN");
    	uDao.saveUser(user);
		
        Reservation r1 = new Reservation();
        r1.setComment("comment101");
        r1.setRoom(room);
        r1.setDate("09/20/2009");
		r1.setUser(user);
        try {
        	r1.setStartTime("2:00 PM");
        	r1.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r1));
		List past = resDao.getPastRoomReservations(room);
		List curr = resDao.getCurrentRoomReservations(room);
		assertEquals(0, past.size());
		assertEquals(1, curr.size());
        
        Reservation r2 = new Reservation();
        r2.setComment("comment102");
        r2.setRoom(room);
        r2.setDate("07/20/2008");
		r2.setUser(user);
        try {
        	r2.setStartTime("4:00 PM");
        	r2.setEndTime("5:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r2));
		past = resDao.getPastRoomReservations(room);
		curr = resDao.getCurrentRoomReservations(room);
		Reservation reservation = (Reservation)past.iterator().next();
		Reservation reservationC = (Reservation)curr.iterator().next();
		assertEquals(1, past.size());
		assertEquals(1, curr.size());
		assertEquals("comment102", reservation.getComment());
		assertEquals("comment101", reservationC.getComment());

        Reservation r3 = new Reservation();
        r3.setComment("comment103");
        r3.setRoom(room);
        r3.setDate("10/20/2009");
		r3.setUser(user);
        try {
        	r3.setStartTime("2:00 PM");
        	r3.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r3));
		past = resDao.getPastRoomReservations(room);
		curr = resDao.getCurrentRoomReservations(room);
		assertEquals(1, past.size());
		assertEquals(2, curr.size());

		Iterator iter = curr.iterator();
		reservation = (Reservation)past.iterator().next();
		reservationC = (Reservation)iter.next();
		assertEquals("comment102", reservation.getComment());
		assertEquals("comment101", reservationC.getComment());
		reservationC = (Reservation)iter.next();
		assertEquals("comment103", reservationC.getComment());
	}

	public void testGetInterval_RoomDaily(){
		config();
		
        Reservation r1 = new Reservation();
        r1.setComment("comment101");
        r1.setRoom(room);
        r1.setDate("09/20/2008");
    	try {
			r1.setStartTime("2:00 PM");
	    	r1.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not produce a parseing error");
		}
        assertTrue(resDao.saveReservation(r1));
        
        Reservation test = new Reservation();
        
        test.setRoom(room);
        test.setDate("09/20/2008");
    	try {
        	test.setStartTime("12:00 AM");
			test.setEndTime("11:59 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not produce a parseing error");
		}
    	
    	List list = resDao.getIntervalReservations(test);
    	assertEquals(1, list.size());
    	Reservation out = (Reservation) list.iterator().next();
    	assertEquals("comment101", out.getComment());
    	assertEquals("2:00 PM", out.getStartTime());
        
    	//add a second for 'today'
        Reservation r2 = new Reservation();
        r2.setComment("comment102");
        r2.setRoom(room);
        r2.setDate("09/20/2008");
		r2.setUser(user);
        try {
        	r2.setStartTime("4:00 PM");
        	r2.setEndTime("5:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r2));

        list = resDao.getIntervalReservations(test);
    	assertEquals(2, list.size());
    	
        //add a reservation for after 'today' 
    	Reservation r3 = new Reservation();
        r3.setComment("comment104");
        r3.setRoom(room);
        r3.setDate("10/20/2008");
		r3.setUser(user);
        try {
        	r3.setStartTime("2:00 PM");
        	r3.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//be sure that it does not get returned
        assertTrue(resDao.saveReservation(r3));
        list = resDao.getIntervalReservations(test);
    	assertEquals(2, list.size());
    	
        //add a reservation for before 'today' 
    	Reservation r4 = new Reservation();
        r4.setComment("comment104");
        r4.setRoom(room);
        r4.setDate("08/20/2008");
		r4.setUser(user);
        try {
        	r4.setStartTime("2:00 PM");
        	r4.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//be sure that it does not get returned
        assertTrue(resDao.saveReservation(r4));
        list = resDao.getIntervalReservations(test);
    	assertEquals(2, list.size());
        
    	//add a reservation for 'today' with a different room
    	Room room2 = new Room();
    	room2.setName("room2");
    	rDao.saveRoom(room2);
    	Reservation r5 = new Reservation();
        r5.setComment("comment105");
        r5.setRoom(room2);
        r5.setDate("09/20/2008");
        try {
        	r5.setStartTime("2:00 PM");
        	r5.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//be sure that it does not get returned
        assertTrue(resDao.saveReservation(r5));
        list = resDao.getIntervalReservations(test);
    	assertEquals(2, list.size());
	
	}

	public void testGetInterval_UserDaily(){
		config();
		
        Reservation r1 = new Reservation();
        r1.setComment("comment101");
        r1.setRoom(room);
        r1.setUser(user);
        r1.setDate("09/20/2008");
    	try {
			r1.setStartTime("2:00 PM");
	    	r1.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not produce a parseing error");
		}
        assertTrue(resDao.saveReservation(r1));
        
        Reservation test = new Reservation();
        
        test.setUser(user);
        test.setDate("09/20/2008");
    	try {
        	test.setStartTime("12:00 AM");
			test.setEndTime("11:59 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not produce a parseing error");
		}
    	
    	List list = resDao.getIntervalReservations(test);
    	assertEquals(1, list.size());
    	Reservation out = (Reservation) list.iterator().next();
    	assertEquals("comment101", out.getComment());
    	assertEquals("2:00 PM", out.getStartTime());
        
    	//add a second for 'today'
        Reservation r2 = new Reservation();
        r2.setComment("comment102");
        r2.setRoom(room);
        r2.setDate("09/20/2008");
		r2.setUser(user);
        try {
        	r2.setStartTime("4:00 PM");
        	r2.setEndTime("5:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
        assertTrue(resDao.saveReservation(r2));

        list = resDao.getIntervalReservations(test);
    	assertEquals(2, list.size());
    	
        //add a reservation for after 'today' 
    	Reservation r3 = new Reservation();
        r3.setComment("comment104");
        r3.setRoom(room);
        r3.setDate("10/20/2008");
		r3.setUser(user);
        try {
        	r3.setStartTime("2:00 PM");
        	r3.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//be sure that it does not get returned
        assertTrue(resDao.saveReservation(r3));
        list = resDao.getIntervalReservations(test);
    	assertEquals(2, list.size());
        //add a reservation for before 'today' 
    	Reservation r4 = new Reservation();
        r4.setComment("comment104");
        r4.setRoom(room);
        r4.setDate("08/20/2008");
		r4.setUser(user);
        try {
        	r4.setStartTime("2:00 PM");
        	r4.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//be sure that it does not get returned
        assertTrue(resDao.saveReservation(r4));
        list = resDao.getIntervalReservations(test);
    	assertEquals(2, list.size());
        
    	//add a reservation for 'today' with a different room
    	Room room2 = new Room();
    	room2.setName("room2");
    	rDao.saveRoom(room2);
    	Reservation r5 = new Reservation();
        r5.setComment("comment105");
        r5.setRoom(room2);
		r5.setUser(user);
        r5.setDate("09/20/2008");
        try {
        	r5.setStartTime("2:00 PM");
        	r5.setEndTime("4:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//be sure that it does get returned
        assertTrue(resDao.saveReservation(r5));
        list = resDao.getIntervalReservations(test);
    	assertEquals(3, list.size());
	
    	//add a reservation for 'today' with a different user
    	User user2 = new User();
    	user2.setFirstName("aaaa");
    	user2.setLastName("zzz");
    	uDao.saveUser(user2);
    	Reservation r6 = new Reservation();
        r6.setComment("comment106");
        r6.setRoom(room);
		r6.setUser(user2);
        r6.setDate("09/20/2008");
        try {
        	r6.setStartTime("9:00 AM");
        	r6.setEndTime("10:00 AM");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//be sure that it does not get returned
        assertTrue(resDao.saveReservation(r6));
        list = resDao.getIntervalReservations(test);
    	assertEquals(3, list.size());
	}
}
