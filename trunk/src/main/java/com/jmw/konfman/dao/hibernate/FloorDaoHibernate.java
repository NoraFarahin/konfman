package com.jmw.konfman.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.jmw.konfman.dao.FloorDao;
import com.jmw.konfman.model.Floor;


/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve Floor objects.
 *
 * @author Matt Raible
 */
@Repository(value = "floorDao")
public class FloorDaoHibernate implements FloorDao {
    HibernateTemplate hibernateTemplate;
    Log logger = LogFactory.getLog(FloorDaoHibernate.class);
    
    @Autowired
    public FloorDaoHibernate(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List getFloors() {
        return hibernateTemplate.loadAll(Floor.class);
    }

    public List getActiveFloors() {
        return hibernateTemplate.find("from Floor f where f.active = true");
    }

    public Floor getFloor(Long id) {
        Floor user = (Floor) hibernateTemplate.get(Floor.class, id);
        if (user == null) {
            throw new ObjectRetrievalFailureException(Floor.class, id);
        }
        return user;
    }

    public void saveFloor(Floor user) {
        hibernateTemplate.saveOrUpdate(user);

        if (logger.isDebugEnabled()) {
            logger.debug("floorId set to: " + user.getId());
        }
    }

    public void removeFloor(Long id) {
        hibernateTemplate.delete(getFloor(id));
    }
}
