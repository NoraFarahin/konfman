package com.jmw.konfman.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.jmw.konfman.dao.FloorDao;
import com.jmw.konfman.model.Floor;

public class FloorManagerImplTest extends MockObjectTestCase {
    private final Log log = LogFactory.getLog(FloorManagerImplTest.class);
    private FloorManagerImpl mgr = new FloorManagerImpl();
    private Mock mockDao = null;

    protected void setUp() throws Exception {
        mockDao = new Mock(FloorDao.class);
        mgr.setFloorDao((FloorDao) mockDao.proxy());
    }

    public void testAddAndRemoveUser() throws Exception {
        Floor floor = new Floor();
        floor.setName("Easter");
        floor.setTitle("Bunny");

        // set expected behavior on dao
        mockDao.expects(once()).method("saveFloor").with(same(floor));

        mgr.saveFloor(floor);

        // verify expectations
        mockDao.verify();

        assertEquals(floor.getName(), "Easter");

        if (log.isDebugEnabled()) {
            log.debug("removing floor...");
        }

        mockDao.expects(once()).method("removeFloor")
                .with(eq(new Long(1)));

        mgr.removeFloor("1");

        // verify expectations
        mockDao.verify();

        try {
            // set expectations
            Throwable ex =
                    new ObjectRetrievalFailureException(Floor.class, "1");
            mockDao.expects(once()).method("getFloor")
                    .with(eq(new Long(1))).will(throwException(ex));

            floor = mgr.getFloor("1");

            // verify expectations
            mockDao.verify();
            fail("Floor 'Easter Bunny' found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
}
