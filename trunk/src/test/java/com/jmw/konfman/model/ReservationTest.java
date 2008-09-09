/**
 * 
 */
package com.jmw.konfman.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

/**
 * @author judahw
 *
 */
public class ReservationTest extends TestCase {
	Reservation reservation;
	/**
	 * @param name
	 */
	public ReservationTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		reservation = new Reservation();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		reservation = null;
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#getId()}.
	 */
	public void testGetId() {
		assertNull(reservation.getId());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setId(java.lang.Long)}.
	 */
	public void testSetId() {
		assertNull(reservation.getId());
		reservation.setId(new Long(1));
		assertEquals(new Long(1), reservation.getId());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#getComment()}.
	 */
	public void testGetSetComment() {
		assertNull(reservation.getComment());
		reservation.setComment("comm");
		assertEquals("comm", reservation.getComment());
	}


	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setStartDateTime(java.util.Date)}.
	 * Test method for {@link com.jmw.konfman.model.Reservation#getStartDateTime()}.
	 */
	public void testGetSetStartDateTime() {
		assertNull(reservation.getStartDateTime());
		Date d = new Date();
		reservation.setStartDateTime(d);
		assertEquals(d, reservation.getStartDateTime());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setEndDateTime(java.util.Date)}.
	 * Test method for {@link com.jmw.konfman.model.Reservation#getEndDateTime()}.
	 */
	public void testGetSetEndDateTime() {
		assertNull(reservation.getEndDateTime());
		Date d = new Date();
		reservation.setEndDateTime(d);
		assertEquals(d, reservation.getEndDateTime());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setRoom(com.jmw.konfman.model.Room)}.
	 * Test method for {@link com.jmw.konfman.model.Reservation#getRoom()}.
	 */
	public void testGetSetRoom() {
		assertNull(reservation.getRoom());
		Room r = new Room();
		r.setId(new Long(111));
		reservation.setRoom(r);
		assertEquals(r, reservation.getRoom());
		assertEquals(111, reservation.getRoom().getId().longValue());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setUser(com.jmw.konfman.model.User)}.
	 * Test method for {@link com.jmw.konfman.model.Reservation#getUser()}.
	 */
	public void testGetSetUser() {
		assertNull(reservation.getUser());
		User u = new User();
		u.setId(new Long(1111));
		reservation.setUser(u);
		assertEquals(u, reservation.getUser());
		assertEquals(1111, reservation.getUser().getId().longValue());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setDate(String)}.
	 * Test method for {@link com.jmw.konfman.model.Reservation#getDate()}.
	 */
	public void testGetSetDate() throws Exception{
		assertNull(reservation.getStartDateTime());
		assertEquals("", reservation.getDate());
		reservation.setDate("01/01/2005");
		reservation.setStartTime("8:30 AM");
		assertEquals("01/01/2005", reservation.getDate());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setDate(java.util.Date)}.
	 * Test method for {@link com.jmw.konfman.model.Reservation#getDate()}.
	 */
	public void testGetSetDateDate() throws Exception{
		assertNull(reservation.getStartDateTime());
		assertEquals("", reservation.getDate());
		Date date = new Date();
		reservation.setDate(date);
		assertEquals(date, reservation.getStartDateTime());
		assertEquals(date, reservation.getEndDateTime());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setStartTime(java.util.Date)}.
	 * Test method for {@link com.jmw.konfman.model.Reservation#getStartTime()}.
	 */
	public void testGetSetStartTime() throws Exception{
		assertNull(reservation.getStartDateTime());
		assertEquals("", reservation.getStartTime());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		Date d = df.parse("01/01/2008 08:30 AM");
		reservation.setDate("01/01/2008");
		reservation.setStartTime("08:30 AM");
		assertEquals(d, reservation.getStartDateTime());
		assertEquals("01/01/2008", reservation.getDate());
		assertEquals("8:30 AM", reservation.getStartTime());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Reservation#setEndTime(java.util.Date)}.
	 * Test method for {@link com.jmw.konfman.model.Reservation#getEndTime()}.
	 */
	public void testGetSetEndTime() throws Exception {
		assertNull(reservation.getEndDateTime());
		assertEquals("", reservation.getEndTime());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		Date d = df.parse("01/01/2008 09:30 AM");
		reservation.setDate("01/01/2008");
		reservation.setStartTime("8:30 AM");
		reservation.setEndTime("9:30 AM");
		assertEquals("01/01/2008", reservation.getDate());
		assertEquals(d, reservation.getEndDateTime());
		assertEquals("9:30 AM", reservation.getEndTime());
	}

	public void testToString(){
		assertEquals("Reservation: null", reservation.toString());
		reservation.setComment("comm");
		assertEquals("Reservation: comm", reservation.toString());
	}
}
