/**
 * 
 */
package com.jmw.konfman.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author judahw
 *
 */
public class FloorTest {
	
	Floor floor = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		floor = new Floor();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		floor = null;
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Floor#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("Floor: null", floor.toString());
		floor.setName("name1");
		assertEquals("Floor: name1", floor.toString());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Floor#setBuilding(com.jmw.konfman.model.Building)}.
	 * Test method for {@link com.jmw.konfman.model.Floor#getBuilding()}.
	 */
	@Test
	public void testGetSetBuilding() {
		assertNull(floor.getBuilding());
		Building b = new Building();
		b.setId(new Long(1000));
		
		floor.setBuilding(b);
		assertEquals(b, floor.getBuilding());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Floor#getId()}.
	 * Test method for {@link com.jmw.konfman.model.Floor#setId(java.lang.Long)}.
	 */
	@Test
	public void testGetSetId() {
		assertNull(floor.getId());
		floor.setId(new Long(11));
		assertEquals(new Long(11), floor.getId());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Floor#getName()}.
	 * Test method for {@link com.jmw.konfman.model.Floor#setName(java.lang.String)}.
	 */
	@Test
	public void testGetSetName() {
		assertNull(floor.getName());
		floor.setName("name1");
		assertEquals("name1", floor.getName());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Floor#getTitle()}.
	 * Test method for {@link com.jmw.konfman.model.Floor#setTitle(java.lang.String)}.
	 */
	@Test
	public void testGetSetTitle() {
		assertNull(floor.getTitle());
		floor.setTitle("Title1");
		assertEquals("Title1", floor.getTitle());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Floor#getComment()}.
	 * Test method for {@link com.jmw.konfman.model.Floor#setComment(java.lang.String)}.
	 */
	@Test
	public void testGetSetComment() {
		assertNull(floor.getComment());
		floor.setComment("Comment1");
		assertEquals("Comment1", floor.getComment());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Floor#isActive()}.
	 * Test method for {@link com.jmw.konfman.model.Floor#setActive(boolean)}.
	 */
	@Test
	public void testIsSetActive() {
		assertFalse(floor.isActive());
		floor.setActive(true);
		assertTrue(floor.isActive());
		floor.setActive(false);
		assertFalse(floor.isActive());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Building#getFloors()}.
	 * Test method for {@link com.jmw.konfman.model.Building#setFloors(java.util.List)}.
	 */
	@Test
	public void testGetSetRooms() {
		assertNull(floor.getRooms());
		List<Room> rooms = new ArrayList<Room>();
		floor.setRooms(rooms);
		assertEquals(0, floor.getRooms().size());
		Room room = new Room();
		room .setId(new Long(8000));
		rooms.add(room);
		assertEquals(1, floor.getRooms().size());
		assertEquals(room, (Room)floor.getRooms().iterator().next());
	}


	/**
	 * Test method for {@link com.jmw.konfman.model.Floor#getFullName()}.
	 */
	@Test
	public void testGetFullName() {
		assertEquals("No Building : null", floor.getFullName());
		floor.setName("name1");
		assertEquals("No Building : name1", floor.getFullName());
		Building b = new Building();
		b.setName("bn");
		floor.setBuilding(b);
		assertEquals("bn : name1", floor.getFullName());
	}

}
