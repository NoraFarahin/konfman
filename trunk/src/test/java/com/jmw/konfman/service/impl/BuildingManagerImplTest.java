package com.jmw.konfman.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.jmw.konfman.dao.BuildingDao;
import com.jmw.konfman.dao.UserDao;
import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.User;
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
    public void testListBuildings() throws Exception {
        Building building1 = new Building();
        building1.setName("b1");
        building1.setTitle("t1");

        Building building2 = new Building();
        building2.setName("b2");
        building2.setTitle("t2");
        // set expected behavior on dao
        mockDao.expects(once()).method("saveBuilding").with(same(building1));
        mgr.saveBuilding(building1);

        // verify expectations
        mockDao.verify();

        //assertEquals(user1.getFullName(), "one, a");

        mockDao.expects(once()).method("saveBuilding").with(same(building2));
        mgr.saveBuilding(building2);

        // verify expectations
        mockDao.verify();

        // verify expectations
        mockDao.verify();
        List buildings = new ArrayList();
        buildings.add(building1);
        buildings.add(building2);
        
        mockDao.expects(once()).method("getBuildings").will(returnValue(buildings));

        List users2 = mgr.getBuildings();

        // verify expectations
        assertEquals(users2.size(), 2);
        mockDao.verify();
    }

    public void testListActiveBuildings() throws Exception {
        Building building1 = new Building();
        building1.setName("b1");
        building1.setTitle("t1");
        building1.setActive(true);

        Building building2 = new Building();
        building2.setName("b2");
        building2.setTitle("t2");
        // set expected behavior on dao
        mockDao.expects(once()).method("saveBuilding").with(same(building1));
        mgr.saveBuilding(building1);

        // verify expectations
        mockDao.verify();

        //assertEquals(user1.getFullName(), "one, a");

        mockDao.expects(once()).method("saveBuilding").with(same(building2));
        mgr.saveBuilding(building2);

        // verify expectations
        mockDao.verify();

        //assertEquals(user1.getFullName(), "one, a");
        // verify expectations
        mockDao.verify();
        List buildings = new ArrayList();
        buildings.add(building1);
        //buildings.add(building2);
        
        mockDao.expects(once()).method("getActiveBuildings").will(returnValue(buildings));

        List users2 = mgr.getActiveBuildings();
        assertEquals(users2.size(), 1);

        // verify expectations
        mockDao.verify();
    }

}
