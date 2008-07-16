package com.jmw.konfman.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.jmw.konfman.dao.UserDao;
import com.jmw.konfman.model.User;

public class UserManagerImplTest extends MockObjectTestCase {
    private final Log log = LogFactory.getLog(UserManagerImplTest.class);
    private UserManagerImpl mgr = new UserManagerImpl();
    private Mock mockDao = null;

    protected void setUp() throws Exception {
        mockDao = new Mock(UserDao.class);
        mgr.setUserDao((UserDao) mockDao.proxy());
    }

    public void testAddAndRemoveUser() throws Exception {
        User user = new User();
        user.setFirstName("Easter");
        user.setLastName("Bunny");

        // set expected behavior on dao
        mockDao.expects(once()).method("saveUser").with(same(user));

        mgr.saveUser(user);

        // verify expectations
        mockDao.verify();

        assertEquals(user.getFullName(), "Bunny, Easter");

        if (log.isDebugEnabled()) {
            log.debug("removing user...");
        }

        mockDao.expects(once()).method("removeUser")
                .with(eq(new Long(1)));

        mgr.removeUser("1");

        // verify expectations
        mockDao.verify();

        try {
            // set expectations
            Throwable ex =
                    new ObjectRetrievalFailureException(User.class, "1");
            mockDao.expects(once()).method("getUser")
                    .with(eq(new Long(1))).will(throwException(ex));

            user = mgr.getUser("1");

            // verify expectations
            mockDao.verify();
            fail("User 'Easter Bunny' found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }

    public void testListUsers() throws Exception {
        User user1 = new User();
        user1.setFirstName("one");
        user1.setLastName("a");

        User user2 = new User();
        user2.setFirstName("two");
        user2.setLastName("b");
        // set expected behavior on dao
        mockDao.expects(once()).method("saveUser").with(same(user1));
        mgr.saveUser(user1);

        // verify expectations
        mockDao.verify();

        //assertEquals(user1.getFullName(), "one, a");

        mockDao.expects(once()).method("saveUser").with(same(user2));
        mgr.saveUser(user2);

        // verify expectations
        mockDao.verify();

        //assertEquals(user1.getFullName(), "one, a");
        // verify expectations
        mockDao.verify();
        List users = new ArrayList();
        users.add(user1);
        users.add(user2);
        
        mockDao.expects(once()).method("getUsers").will(returnValue(users));

        List users2 = mgr.getUsers();

        // verify expectations
        mockDao.verify();
    }

}
