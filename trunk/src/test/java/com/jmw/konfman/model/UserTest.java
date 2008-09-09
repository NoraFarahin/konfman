package com.jmw.konfman.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
/**
 * @author judahw
 *
 */
public class UserTest extends TestCase {

	User user = null;
	
	/**
	 * @param name
	 */
	public UserTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getId()}.
	 */
	public void testGetSetId() {
		assertEquals(null, user.getId());
		user.setId(new Long(11));
		assertEquals(new Long(11), user.getId());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getFirstName()}.
	 * Test method for {@link com.jmw.konfman.model.User#setFirstName(java.lang.String)}.
	 */
	public void testGetSetFirstName() {
		assertEquals(null, user.getFirstName());
		user.setFirstName("FN1");
		assertEquals("FN1", user.getFirstName());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getLastName()}.
	 * Test method for {@link com.jmw.konfman.model.User#setLastName(java.lang.String)}.
	 */
	public void testGetSetLastName() {
		assertEquals(null, user.getLastName());
		user.setLastName("LN1");
		assertEquals("LN1", user.getLastName());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getPhone()}.
	 * Test method for {@link com.jmw.konfman.model.User#setPhone(java.lang.String)}.
	 */
	public void testGetSetPhone() {
		assertEquals(null, user.getPhone());
		user.setPhone("phone1");
		assertEquals("phone1", user.getPhone());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getEmail()}.
	 * Test method for {@link com.jmw.konfman.model.User#setEmail(java.lang.String)}.
	 */
	public void testGetSetEmail() {
		assertEquals(null, user.getEmail());
		user.setEmail("e1");
		assertEquals("e1", user.getEmail());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getPassword()}.
	 * Test method for {@link com.jmw.konfman.model.User#setPassword(java.lang.String)}.
	 */
	public void testGetSetPassword() {
		assertEquals(null, user.getPassword());
		user.setPassword("FN1");
		assertEquals("FN1", user.getPassword());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getAdminStatus()}.
	 * Test method for {@link com.jmw.konfman.model.User#setAdminStatus(java.lang.String)}.
	 */
	public void testGetSetAdminStatus() {
		assertEquals(null, user.getAdminStatus());
		user.setAdminStatus("FN1");
		assertEquals("FN1", user.getAdminStatus());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getDefaultFloor()}.
	 */
	public void testGetSetDefaultFloor() {
		assertNull(user.getDefaultFloor());
		Floor floor = new Floor();
		floor.setId(new Long(1000));
		floor.setName("name");
		
		user.setDefaultFloor(floor);
		assertNotNull(user.getDefaultFloor());
		
		Floor f1 = user.getDefaultFloor();
		assertEquals(new Long(1000), f1.getId());
		assertEquals("name", f1.getName());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getFullName()}.
	 */
	public void testGetFullName() {
		assertEquals("null, null", user.getFullName());
		user.setFirstName("first");
		user.setLastName("last");
		assertEquals("last, first", user.getFullName());
	}


	public void testGetSetReservations(){
		assertNull(user.getReservations());
		List<Reservation> reservations = new ArrayList<Reservation>();
		Reservation res = new Reservation();
		res.setId(new Long(2000));
		reservations.add(res);
		user.setReservations(reservations);
		assertEquals(1, user.getReservations().size());
		assertEquals(2000, user.getReservations().get(0).getId().longValue());
	}
	
	public void testGetSetAdministeredRooms(){
		assertNotNull(user.getAdministeredRooms());
		assertEquals(0, user.getAdministeredRooms().size());
		Set<Room> rooms = new HashSet<Room>();
		Room room = new Room();
		room.setId(new Long(3000));
		rooms.add(room);
		user.setAdministeredRooms(rooms);
		assertEquals(1, user.getAdministeredRooms().size());
		Room room1 = (Room)user.getAdministeredRooms().iterator().next();
		assertEquals(3000, room1.getId().longValue());
	}
	
	public void testAddRemoveAdministeredRooms(){
		assertNotNull(user.getAdministeredRooms());
		assertEquals(0, user.getAdministeredRooms().size());
		Room room = new Room();
		room.setId(new Long(3300));
		
		//add the room
		user.addAdministeredRoom(room);
		assertEquals(1, user.getAdministeredRooms().size());
		Room room1 = (Room)user.getAdministeredRooms().iterator().next();
		assertEquals(3300, room1.getId().longValue());
		
		//try adding it again to be sure that it cannot be added twice
		user.addAdministeredRoom(room);
		assertEquals(1, user.getAdministeredRooms().size());
		
		//try to remove it
		user.removeAdminsteredRoom(room1);
		assertEquals(0, user.getAdministeredRooms().size());
	}
	
	public void testEquals(){
		assertFalse(user.equals(null));
		assertFalse(user.equals(new User()));
		assertFalse(user.equals(new Room()));
		
		user.setId(new Long(4000));
		assertFalse(user.equals(new User()));
		User u2 = new User();
		u2.setId(new Long(4000));
		assertTrue(user.equals(u2));
		assertTrue(u2.equals(user));
		
		u2.setId(new Long(4001));
		assertFalse(user.equals(u2));
		assertFalse(u2.equals(user));
	}
	
	public void testHashCode(){
		assertEquals(0, user.hashCode());

		user.setId(new Long(5000));
		assertEquals(new Long(5000).hashCode(), user.hashCode());
	}
}
