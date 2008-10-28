package com.jmw.konfman.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.jmw.konfman.dao.ReservationDao;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;


/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve User objects.
 *
 * @author JMW
 */
@Repository(value = "reservationDao")
public class ReservationDaoHibernate implements ReservationDao {
    HibernateTemplate hibernateTemplate;
    Log logger = LogFactory.getLog(ReservationDaoHibernate.class);
    
    @Autowired
    public ReservationDaoHibernate(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List getReservations() {
        return hibernateTemplate.loadAll(Reservation.class);
    }

    public Reservation getReservation(Long id) {
        Reservation reservation = (Reservation) hibernateTemplate.get(Reservation.class, id);
        if (reservation == null) {
            throw new ObjectRetrievalFailureException(Reservation.class, id);
        }
        return reservation;
    }

    public boolean saveReservation(Reservation reservation) {
    	boolean b = isConflict(reservation);
    	if (b == true){
            logger.debug("conflict discovered");
    		return false;
    	}
        hibernateTemplate.saveOrUpdate(reservation);

        logger.debug("Saved Reservation Id #" + reservation.getId());
        return true;
    }

    public void removeReservation(Long id) {
        hibernateTemplate.delete(getReservation(id));
        logger.debug("Deleted Reservation Id #" + id);
    }

	public boolean isConflict(Reservation reservation) {
		//increment start time and end time to prevent overlap conflicts for 
		//reservations that begin where another ends
		Date startDateTime = new Date(reservation.getStartDateTime().getTime() + 1000);
		Date endDateTime = new Date(reservation.getEndDateTime().getTime() - 1000);

		//this HQL checks for three conditions:
		//1) If the start time overlaps with and existing reservation
		//2) If the end time overlaps with an existing reservation
		//3) If the entire reservation is longer than any existing reservation
		List list = null;
		if (reservation.getId() == null){
			Object [] params = new Object[5];
			params[0] = reservation.getRoom();
			params[1] = startDateTime;
			params[2] = endDateTime;
			params[3] = startDateTime;
			params[4] = endDateTime;
			list = hibernateTemplate.find("from Reservation r where r.room = ? and ((? between r.startDateTime and r.endDateTime) or ((? between r.startDateTime and r.endDateTime)) or (? < r.startDateTime and ? > r.endDateTime))", params);
		} else {
			Object [] params = new Object[6];
			params[0] = reservation.getId();
			params[1] = reservation.getRoom();
			params[2] = startDateTime;
			params[3] = endDateTime;
			params[4] = startDateTime;
			params[5] = endDateTime;
			list = hibernateTemplate.find("from Reservation r where r.id != ? and r.room = ? and ((? between r.startDateTime and r.endDateTime) or ((? between r.startDateTime and r.endDateTime)) or (? < r.startDateTime and ? > r.endDateTime))", params);
		}
		logger.debug("Retreiving matching reservations, count: " + list.size());
		if (list.size() > 0){
			return true;
		}
		return false;
	}

	public List getAllUserReservations(User user) {
		return 	hibernateTemplate.find("from Reservation r where r.user = ? order by r.startDateTime", user);
	}

	public List getCurrentUserReservations(User user) {
		Date date = new Date();
		logger.debug("returning current reservations for after: " + date);
		Object [] params = new Object[2];
		params[0] = user;
		params[1] = date;
		return 	hibernateTemplate.find("from Reservation r where r.user = ? and r.startDateTime >= ? order by r.startDateTime", params);
	}

	public List getPastUserReservations(User user) {
		Date date = new Date();
		logger.debug("returning past reservations for before: " + date);
		Object [] params = new Object[2];
		params[0] = user;
		params[1] = date;
		return 	hibernateTemplate.find("from Reservation r where r.user = ? and r.startDateTime < ? order by r.startDateTime", params);
	}

	public List getIntervalReservations(Reservation reservation){
		List list = null;
		Object [] params; 
		int fieldCount = 4;
		StringBuffer whereClause = new StringBuffer();
		if (reservation.getRoom() != null){
			fieldCount++;
			whereClause.append("r.room = ? and ");
		}
		
		if (reservation.getUser() != null){
			fieldCount++;
			whereClause.append("r.user = ? and ");
		}
		params = new Object[fieldCount];
		int paramCount = 0;
		if (reservation.getRoom() != null){
			params[paramCount++] = reservation.getRoom();
		}
		if (reservation.getUser() != null){
			params[paramCount++] = reservation.getUser();
		}
		params[paramCount++] = reservation.getStartDateTime();
		params[paramCount++] = reservation.getEndDateTime();
		params[paramCount++] = reservation.getStartDateTime();
		params[paramCount++] = reservation.getEndDateTime();
		
		list = hibernateTemplate.find("from Reservation r where " + whereClause.toString() + "((? between r.startDateTime and r.endDateTime) or ((? between r.startDateTime and r.endDateTime)) or (? < r.startDateTime and ? > r.endDateTime)) order by r.startDateTime", params);
		return list;
	}
	
	
	public List getAllRoomReservations(Room room) {
		return 	hibernateTemplate.find("from Reservation r where r.room = ? order by r.startDateTime", room);
	}

	public List getCurrentRoomReservations(Room room) {
		Date date = new Date();
		logger.debug("returning current reservations for after: " + date);
		Object [] params = new Object[2];
		params[0] = room;
		params[1] = date;
		return 	hibernateTemplate.find("from Reservation r where r.room = ? and r.startDateTime >= ? order by r.startDateTime", params);
	}

	public List getPastRoomReservations(Room room) {
		Date date = new Date();
		logger.debug("returning past reservations for before: " + date);
		Object [] params = new Object[2];
		params[0] = room;
		params[1] = date;
		return 	hibernateTemplate.find("from Reservation r where r.room = ? and r.startDateTime < ? order by r.startDateTime)", params);
	}
}
