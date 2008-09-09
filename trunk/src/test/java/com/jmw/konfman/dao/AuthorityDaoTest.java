package com.jmw.konfman.dao;

import org.springframework.dao.DataAccessException;

import com.jmw.konfman.model.Authority;

public class AuthorityDaoTest extends BaseDaoTestCase {
    private Authority authority = null;
    private AuthorityDao dao = null;

    public void setAuthorityDao(AuthorityDao userDao) {
        this.dao = userDao;
    }

    public void testGetAuthorities() {
    	int currentAuthorityCount = dao.getAuthorities().size();
    	
        authority = new Authority();
        authority.setRole("name");
        //authority.setTitle("title");

        dao.saveAuthority(authority);

        assertEquals(currentAuthorityCount + 1, dao.getAuthorities().size());
    }

    public void testSaveAuthority() throws Exception {
        authority = new Authority();
        authority.setRole("Rod");

        dao.saveAuthority(authority);
        assertTrue("primary key assigned", authority.getId() != null);

        assertNotNull(authority.getRole());
    }

    public void testAddAndRemoveAuthority() throws Exception {
    	int currentAuthorityCount = dao.getAuthorities().size();

    	authority = new Authority();
        authority.setRole("Bill");

        dao.saveAuthority(authority);
        assertEquals(currentAuthorityCount + 1, dao.getAuthorities().size());
        assertNotNull(authority.getId());
        assertTrue(authority.getRole().equals("Bill"));

        log.debug("removing authority...");
        dao.removeAuthority(authority.getId());
        //should be back where we started
        assertEquals(currentAuthorityCount, dao.getAuthorities().size());

        try {
            authority = dao.getAuthority(authority.getId());
            fail("Authority found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
}
