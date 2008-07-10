package com.jmw.konfman.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.jmw.konfman.dao.BuildingDao;
import com.jmw.konfman.dao.UserDao;
import com.jmw.konfman.model.Building;
import com.jmw.konfman.service.impl.BuildingManagerImpl;

public class BuildingManagerImplTest extends MockObjectTestCase {
    private final Log log = LogFactory.getLog(BuildingManagerImplTest.class);
    private BuildingManagerImpl mgr = new BuildingManagerImpl();
    private Mock mockDao = null;

    protected void setUp() throws Exception {
        mockDao = new Mock(BuildingDao.class);
        mgr.setBuildingDao((BuildingDao) mockDao.proxy());
    }

    public void testAddAndRemoveUser() throws Exception {
        Building building = new Building();
        building.setName("Easter");
        building.setTitle("Bunny");

        // set expected behavior on dao
        mockDao.expects(once()).method("saveBuilding").with(same(building));

        mgr.saveBuilding(building);

        // verify expectations
        mockDao.verify();

        assertEquals(building.getName(), "Easter");

        if (log.isDebugEnabled()) {
            log.debug("removing building...");
        }

        mockDao.expects(once()).method("removeBuilding")
                .with(eq(new Long(1)));

        mgr.removeBuilding("1");

        // verify expectations
        mockDao.verify();

        try {
            // set expectations
            Throwable ex =
                    new ObjectRetrievalFailureException(Building.class, "1");
            mockDao.expects(once()).method("getBuilding")
                    .with(eq(new Long(1))).will(throwException(ex));

            building = mgr.getBuilding("1");

            // verify expectations
            mockDao.verify();
            fail("Building 'Easter Bunny' found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
}
