package com.jmw.konfman.model.wrapper;

import java.util.List;

import junit.framework.TestCase;

public class ReservationWrapperTest extends TestCase {

	ReservationWrapper rw;
	public ReservationWrapperTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		rw = new ReservationWrapper();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		rw = null;
	}

	public void testReservationWrapper() {
		assertNull(rw.getComment());
		assertNull(rw.getDate());
		assertNull(rw.getEndTime());
		assertNull(rw.getStartTime());
	}

	public void testReservationWrapperReservation() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetReservation() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetReservation() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetId() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetId() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetComment() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetComment() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetRoom() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetRoom() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetUser() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetUser() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetDate() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetDate() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetStartTime() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetStartTime() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetEndTime() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetEndTime() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetUserId() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetRoomId() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetRoomDao() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetUserDao() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetUsers() {
		List users = rw.getUsers();
		assertTrue(users.size() > 0);
	}

}
