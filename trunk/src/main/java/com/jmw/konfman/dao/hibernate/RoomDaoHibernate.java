package com.jmw.konfman.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.jmw.konfman.dao.RoomDao;
import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Room;


/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve Floor objects.
 *
 * @author Matt Raible
 */
@Repository(value = "roomDao")
public class RoomDaoHibernate implements RoomDao {
    HibernateTemplate hibernateTemplate;
    Log logger = LogFactory.getLog(FloorDaoHibernate.class);
    
    @Autowired
    public RoomDaoHibernate(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List getRooms() {
        return hibernateTemplate.loadAll(Room.class);
    }

    public List getActiveRooms() {
        return hibernateTemplate.find("from Room r where r.active = true");
    }

    public Room getRoom(Long id) {
        Room room = (Room) hibernateTemplate.get(Room.class, id);
        if (room == null) {
            throw new ObjectRetrievalFailureException(Room.class, id);
        }
        return room;
    }

    public void saveRoom(Room room) {
        hibernateTemplate.saveOrUpdate(room);
        hibernateTemplate.flush();
        if (logger.isDebugEnabled()) {
            logger.debug("Room Id set to: " + room.getId());
        }
    }

    public void removeRoom(Long id) {
        hibernateTemplate.delete(getRoom(id));
    }
}
