package com.jmw.konfman.dao.hibernate;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jmw.konfman.dao.BuildingDao;
import com.jmw.konfman.model.Building;

import java.util.List;


/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve Building objects.
 *
 * @author Judah W
 */
@Repository(value = "buildingDao")
public class BuildingDaoHibernate implements BuildingDao {
    HibernateTemplate hibernateTemplate;
    Log logger = LogFactory.getLog(BuildingDaoHibernate.class);
    
    @Autowired
    public BuildingDaoHibernate(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List getBuildings() {
        return hibernateTemplate.loadAll(Building.class);
    }

    public List getActiveBuildings() {
        return hibernateTemplate.find("from Building b where b.active = true");
    }

    public Building getBuilding(Long id) {
        Building user = (Building) hibernateTemplate.get(Building.class, id);
        if (user == null) {
            throw new ObjectRetrievalFailureException(Building.class, id);
        }
        return user;
    }

    public void saveBuilding(Building user) {
        hibernateTemplate.saveOrUpdate(user);

        if (logger.isDebugEnabled()) {
            logger.debug("userId set to: " + user.getId());
        }
    }

    public void removeBuilding(Long id) {
        hibernateTemplate.delete(getBuilding(id));
    }
}
