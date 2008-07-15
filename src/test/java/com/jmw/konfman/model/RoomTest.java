package com.jmw.konfman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RoomTest {
	
	Room room = null;

	@Before
	public void setUp() throws Exception {
		room = new Room();
	}

	@After
	public void tearDown() throws Exception {
		room = null;
	}

	@Test
	public void testHashCode() {
		assertEquals(0, room.hashCode());

		room.setId(new Long(5000));
		assertEquals(new Long(5000).hashCode(), room.hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("Room: null", room.toString());
		room.setName("name1");
		assertEquals("Room: name1", room.toString());
	}

	@Test
	public void testGetSetFloor() {
		assertNull(room.getFloor());
		Floor floor = new Floor();
		floor.setId(new Long(1000));
		room.setFloor(floor);
		
		assertEquals(floor, room.getFloor());
	}


	@Test
	public void testGetSetId() {
		assertNull(room.getId());
		room.setId(new Long(2000));
		assertEquals(new Long(2000), room.getId());
	}

	@Test
	public void testGetSetName() {
		assertNull(room.getName());
		room.setName("name1");
		assertEquals("name1", room.getName());
	}

	@Test
	public void testGetSetTitle() {
		assertNull(room.getTitle());
		room.setTitle("Title1");
		assertEquals("Title1", room.getTitle());
	}

	@Test
	public void testGetSetComment() {
		assertNull(room.getComment());
		room.setComment("Comment1");
		assertEquals("Comment1", room.getComment());
	}

	@Test
	public void testIsSetActive() {
		assertFalse(room.isActive());
		room.setActive(true);
		assertTrue(room.isActive());
		room.setActive(false);
		assertFalse(room.isActive());
	}

	@Test
	public void testGetSetAttributes() {
		assertNull(room.getAttributes());
		room.setAttributes("Attributes1");
		assertEquals("Attributes1", room.getAttributes());
	}

	@Test
	public void testGetSetReservations() {
		assertNull(room.getReservations());
		List<Reservation> reservations = new ArrayList<Reservation>();
		room.setReservations(reservations);
		assertEquals(0, room.getReservations().size());
		Reservation res = new Reservation();
		res.setId(new Long(6000));
		reservations.add(res);
		assertEquals(1, room.getReservations().size());
		assertEquals(res, (Reservation)room.getReservations().iterator().next());
	}

	@Test
	public void testGetSetAdministrators() {
		assertNull(room.getAdministrators());
		Set<User> users = new HashSet<User>();
		room.setAdministrators(users);
		assertEquals(0, room.getAdministrators().size());
		User u = new User();
		u.setId(new Long(3000));
		users.add(u);
		assertEquals(1, room.getAdministrators().size());
		assertEquals(u, (User)room.getAdministrators().iterator().next());
	}

	@Test
	public void testAddRemoveAdministrator() {
		assertNull(room.getAdministrators());
		Set<User> users = new HashSet<User>();
		room.setAdministrators(users);
		assertEquals(0, room.getAdministrators().size());
		User u = new User();
		u.setId(new Long(3000));
		assertTrue(room.addAdministrator(u));
		assertEquals(1, room.getAdministrators().size());
		assertEquals(u, (User)room.getAdministrators().iterator().next());

		//try it again and make sure that the 
		assertFalse(room.addAdministrator(u));
		assertEquals(1, room.getAdministrators().size());

		assertTrue(room.removeAdministrator(u));
		assertEquals(0, room.getAdministrators().size());
		assertFalse(room.removeAdministrator(u));
	}

	@Test
	public void testEqualsObject() {
		assertFalse(room.equals(null));
		assertFalse(room.equals(new Room()));
		assertFalse(room.equals(new Reservation()));
		
		room.setId(new Long(4000));
		assertFalse(room.equals(new Room()));
		Room r2 = new Room();
		r2.setId(new Long(4000));
		assertTrue(room.equals(r2));
		assertTrue(r2.equals(room));
		
		r2.setId(new Long(4001));
		assertFalse(room.equals(r2));
		assertFalse(r2.equals(room));
	}

}
