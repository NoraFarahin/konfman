package com.jmw.konfman.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.jmw.konfman.dao.RoomDao;
import com.jmw.konfman.model.Room;

public class RoomManagerImplTest extends MockObjectTestCase {
    private final Log log = LogFactory.getLog(RoomManagerImplTest.class);
    private RoomManagerImpl mgr = new RoomManagerImpl();
    private Mock mockDao = null;

    protected void setUp() throws Exception {
        mockDao = new Mock(RoomDao.class);
        mgr.setRoomDao((RoomDao) mockDao.proxy());
    }

    public void testAddAndRemoveUser() throws Exception {
        Room room = new Room();
        room.setName("Easter");
        room.setTitle("Bunny");

        // set expected behavior on dao
        mockDao.expects(once()).method("saveRoom").with(same(room));

        mgr.saveRoom(room);

        // verify expectations
        mockDao.verify();

        assertEquals(room.getName(), "Easter");

        if (log.isDebugEnabled()) {
            log.debug("removing room...");
        }

        mockDao.expects(once()).method("removeRoom")
                .with(eq(new Long(1)));

        mgr.removeRoom("1");

        // verify expectations
        mockDao.verify();

        try {
            // set expectations
            Throwable ex =
                    new ObjectRetrievalFailureException(Room.class, "1");
            mockDao.expects(once()).method("getRoom")
                    .with(eq(new Long(1))).will(throwException(ex));

            room = mgr.getRoom("1");

            // verify expectations
            mockDao.verify();
            fail("Room 'Easter Bunny' found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
}
