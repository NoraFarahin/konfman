package com.jmw.konfman.dao;

import org.springframework.dao.DataAccessException;

import com.jmw.konfman.dao.UserDao;
import com.jmw.konfman.model.User;

public class UserDaoTest extends BaseDaoTestCase {
    private User user = null;
    private UserDao dao = null;

    public void setUserDao(UserDao userDao) {
        this.dao = userDao;
    }

    public void testGetUsers() {
        user = new User();
        user.setFirstName("Rod");
        user.setLastName("Johnson");

        dao.saveUser(user);

        assertTrue(dao.getUsers().size() >= 1);
    }

    public void testSaveUser() throws Exception {
        user = new User();
        user.setFirstName("Rod");
        user.setLastName("Johnson");

        dao.saveUser(user);
        assertTrue("primary key assigned", user.getId() != null);

        assertNotNull(user.getFirstName());
    }

    public void testAddAndRemoveUser() throws Exception {
        user = new User();
        user.setFirstName("Bill");
        user.setLastName("Joy");

        dao.saveUser(user);

        assertNotNull(user.getId());
        assertTrue(user.getFirstName().equals("Bill"));

        log.debug("removing user...");

        dao.removeUser(user.getId());
        endTransaction();

        try {
            user = dao.getUser(user.getId());
            fail("User found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
    
    public void testGetUserByUsername_null(){
    	assertNull(dao.getUser("zzzzzzzz111111111111111111"));
    }
    
    
    public void testGetUserByUsername(){
        user = new User();
        user.setFirstName("Billy");
        user.setLastName("Josef");
        user.setUsername("bj");

        dao.saveUser(user);

        assertNotNull(user.getId());
        assertTrue(user.getFirstName().equals("Billy"));

        log.debug("removing user...");

        User user1 = dao.getUser("bj");
        assertNotNull(user1.getId());
        assertTrue(user1.getFirstName().equals("Billy"));
    }
}
