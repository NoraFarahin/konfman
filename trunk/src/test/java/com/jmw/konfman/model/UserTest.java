package com.jmw.konfman.model;

import junit.framework.TestCase;
/**
 * @author judahw
 *
 */
public class UserTest extends TestCase {

	User user = new User();
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
	public void testGetDefaultFloor() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#setDefaultFloor(int)}.
	 */
	public void testSetDefaultFloor() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.User#getFullName()}.
	 */
	public void testGetFullName() {
		assertEquals(null, user.getFullName());
		user.setFirstName("first");
		user.setLastName("last");
		assertEquals("last, first", user.getFullName());
	}

}
