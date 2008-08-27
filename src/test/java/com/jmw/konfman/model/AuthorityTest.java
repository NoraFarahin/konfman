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
public class AuthorityTest {
	Authority authority = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		authority = new Authority();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		authority = null;
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Authority#toString()}.
	 *
	@Test
	public void testToString() {
		assertEquals("Authority: null", authority.toString());
		authority.setName("name1");
		assertEquals("Authority: name1", authority.toString());
	}*/

	/**
	 * Test method for {@link com.jmw.konfman.model.Authority#getId()}.
	 * Test method for {@link com.jmw.konfman.model.Authority#setId(java.lang.Long)}.
	 */
	@Test
	public void testGetSetId() {
		assertNull(authority.getId());
		authority.setId(new Long(11));
		assertEquals(new Long(11), authority.getId());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Authority#getRoll()}.
	 * Test method for {@link com.jmw.konfman.model.Authority#setRoll(java.lang.String)}.
	 */
	@Test
	public void testGetSetRoll() {
		assertNull(authority.getRole());
		authority.setRole("Roll1");
		assertEquals("Roll1", authority.getRole());
	}

	/**
	 * Test method for {@link com.jmw.konfman.model.Authority#getTitle()}.
	 * Test method for {@link com.jmw.konfman.model.Authority#setTitle(java.lang.String)}.
	 *
	@Test
	public void testGetSetTitle() {
		assertNull(authority.getTitle());
		authority.setTitle("Title1");
		assertEquals("Title1", authority.getTitle());
	}*/

}
