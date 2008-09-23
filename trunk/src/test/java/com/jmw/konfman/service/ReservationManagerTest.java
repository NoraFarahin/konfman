package com.jmw.konfman.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.jmw.konfman.dao.BuildingDao;
import com.jmw.konfman.dao.FloorDao;
import com.jmw.konfman.dao.ReservationDao;
import com.jmw.konfman.dao.RoomDao;
import com.jmw.konfman.dao.UserDao;
import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;

public class ReservationManagerTest extends AbstractTransactionalDataSourceSpringContextTests {
	private Building building;
	@Autowired
	private BuildingDao bDao;
    
	private Floor floor;
	@Autowired
	private FloorDao dao;
    
    private Room room;
    @Autowired
    private RoomDao rDao;
    
    private User user;
    @Autowired
    private UserDao uDao;
    
    private Reservation res;
    @Autowired
    private ReservationDao resDao;
    @Autowired
    private ReservationManager reservationManager = null;
    
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

    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        return new String[] {"classpath*:/WEB-INF/applicationContext*.xml"};
    }

    /*
    public void testGetSaveRemoveReservations() {
        int reservationCount = reservationManager.getReservations().size();
    	Reservation reservation1 = new Reservation();
        reservation1.setComment("Abbie");
        Reservation reservation2 = new Reservation();
        reservation2.setComment("Jack");
        reservationManager.saveReservation(reservation1);
        reservationManager.saveReservation(reservation2);

        assertEquals(reservationCount + 2, reservationManager.getReservations().size());
        
        reservationManager.removeReservation(reservation1.getId().toString());
        assertEquals(reservationCount + 1, reservationManager.getReservations().size());
                
        reservationManager.removeReservation(reservation2.getId().toString());
        assertEquals(reservationCount, reservationManager.getReservations().size());
        
        try{
            reservationManager.removeReservation(reservation2.getId().toString());
            fail("Should not be able to remove an object that was removed already!");
        }catch (Exception e){}
        assertEquals(reservationCount, reservationManager.getReservations().size());
    }*/
    
    public void testGetDailyWeeklyMonthlyRoomReservations(){
    	config();
    	//first on day
    	Reservation r1 = new Reservation();
    	r1.setRoom(room);
    	r1.setDate("09/08/2008");
    	try {
			r1.setStartTime("1:00 PM");
			r1.setEndTime("1:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r1);

    	//test
		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse("09/08/2008");
		} catch (ParseException e) {
			e.printStackTrace();
			fail("should not produce a parse error");
		}
		List list = reservationManager.getDailyRoomReservations(room, date);
		assertEquals(1, list.size());

    	//second on day
		Reservation r2 = new Reservation();
    	r2.setRoom(room);
    	r2.setDate("09/08/2008");
    	try {
			r2.setStartTime("2:00 PM");
			r2.setEndTime("3:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r2);
		
    	//for week
		Reservation r3 = new Reservation();
    	r3.setRoom(room);
    	r3.setDate("09/09/2008");
    	try {
			r3.setStartTime("12:00 PM");
			r3.setEndTime("1:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r3);

    	//for month
		Reservation r4 = new Reservation();
    	r4.setRoom(room);
    	r4.setDate("09/29/2008");
    	try {
			r4.setStartTime("12:00 PM");
			r4.setEndTime("1:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r4);
		
		//out of month
		Reservation r5 = new Reservation();
    	r5.setRoom(room);
    	r5.setDate("10/08/2008");
    	try {
			r5.setStartTime("12:00 PM");
			r5.setEndTime("1:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r5);
		
		//different use
		Room room2 = new Room();
		room2.setName("name");
		rDao.saveRoom(room2);
		Reservation r6 = new Reservation();
    	r6.setRoom(room2);
    	r6.setDate("09/08/2008");
    	try {
			r6.setStartTime("12:00 PM");
			r6.setEndTime("1:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r6);


		//there should be 2 daily
		list = reservationManager.getDailyRoomReservations(room, date);
		assertEquals(2, list.size());
    	//there should be 3 weekly
		list = reservationManager.getWeeklyRoomReservations(room, date);
		assertEquals(3, list.size());
    	//there should be 4 monthly
		list = reservationManager.getMonthlyRoomReservations(room, date);
		assertEquals(4, list.size());
    }

    
    public void testGetDailyWeeklyMonthlyUserReservations(){
    	config();
    	//first on day
    	Reservation r1 = new Reservation();
    	r1.setUser(user);
    	r1.setDate("09/08/2008");
    	try {
			r1.setStartTime("1:00 PM");
			r1.setEndTime("1:30 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r1);

    	//test
		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse("09/08/2008");
		} catch (ParseException e) {
			e.printStackTrace();
			fail("should not produce a parse error");
		}
		List list = reservationManager.getDailyUserReservations(user, date);
		assertEquals(1, list.size());

    	//second on day
		Reservation r2 = new Reservation();
    	r2.setRoom(room);
    	r2.setUser(user);
    	r2.setDate("09/08/2008");
    	try {
			r2.setStartTime("2:00 PM");
			r2.setEndTime("3:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r2);
		
    	//for week
		Reservation r3 = new Reservation();
    	r3.setUser(user);
    	r3.setDate("09/09/2008");
    	try {
			r3.setStartTime("12:00 PM");
			r3.setEndTime("1:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r3);

    	//for month
		Reservation r4 = new Reservation();
    	r4.setRoom(room);
    	r4.setUser(user);
    	r4.setDate("09/29/2008");
    	try {
			r4.setStartTime("12:00 PM");
			r4.setEndTime("1:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r4);
		
		//out of month
		Reservation r5 = new Reservation();
    	r5.setUser(user);
    	r5.setRoom(room);
    	r5.setDate("10/08/2008");
    	try {
			r5.setStartTime("12:00 PM");
			r5.setEndTime("1:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r5);
		
		//different use
		User user2 = new User();
		user2.setFirstName("ffffffffffff");
		uDao.saveUser(user2);
		Reservation r6 = new Reservation();
    	r6.setUser(user2);
    	r6.setRoom(room);
    	r6.setDate("09/08/2008");
    	try {
			r6.setStartTime("12:00 PM");
			r6.setEndTime("1:00 PM");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not generate parse errors.");
		}
		resDao.saveReservation(r6);

		//there should be 2 daily
		list = reservationManager.getDailyUserReservations(user, date);
		assertEquals(2, list.size());
    	//there should be 3 weekly
		list = reservationManager.getWeeklyUserReservations(user, date);
		assertEquals(3, list.size());
    	//there should be 4 monthly
		list = reservationManager.getMonthlyUserReservations(user, date);
		assertEquals(4, list.size());
    }

}
