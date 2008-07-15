/**
 * 
 */
package com.jmw.konfman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author judahw
 *
 */
public class BuildingTest {
	
	Building building = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		building = new Building();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		building = null;
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Building#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("Building: null", building.toString());
		building.setName("name1");
		assertEquals("Building: name1", building.toString());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Building#getId()}.
	 * Test method for {@link com.jmw.konfman.model.Building#setId(java.lang.Long)}.
	 */
	@Test
	public void testGetSetId() {
		assertNull(building.getId());
		building.setId(new Long(11));
		assertEquals(new Long(11), building.getId());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Building#getName()}.
	 * Test method for {@link com.jmw.konfman.model.Building#setName(java.lang.String)}.
	 */
	@Test
	public void testGetSetName() {
		assertNull(building.getName());
		building.setName("name1");
		assertEquals("name1", building.getName());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Building#getTitle()}.
	 * Test method for {@link com.jmw.konfman.model.Building#setTitle(java.lang.String)}.
	 */
	@Test
	public void testGetSetTitle() {
		assertNull(building.getTitle());
		building.setTitle("Title1");
		assertEquals("Title1", building.getTitle());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Building#getComment()}.
	 * Test method for {@link com.jmw.konfman.model.Building#setComment(java.lang.String)}.
	 */
	@Test
	public void testGetSetComment() {
		assertNull(building.getComment());
		building.setComment("Comment1");
		assertEquals("Comment1", building.getComment());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Building#isActive()}.
	 * Test method for {@link com.jmw.konfman.model.Building#setActive(boolean)}.
	 */
	@Test
	public void testIsSetActive() {
		assertFalse(building.isActive());
		building.setActive(true);
		assertTrue(building.isActive());
		building.setActive(false);
		assertFalse(building.isActive());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Building#getFloors()}.
	 * Test method for {@link com.jmw.konfman.model.Building#setFloors(java.util.List)}.
	 */
	@Test
	public void testGetSetFloors() {
		assertNull(building.getFloors());
		List<Floor> floors = new ArrayList<Floor>();
		building.setFloors(floors);
		assertEquals(0, building.getFloors().size());
		Floor floor = new Floor();
		floor.setId(new Long(8000));
		floors.add(floor);
		assertEquals(1, building.getFloors().size());
		assertEquals(floor, (Floor)building.getFloors().iterator().next());
	}

}
