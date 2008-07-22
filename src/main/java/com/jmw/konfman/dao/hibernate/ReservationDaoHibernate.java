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
        return hibernateTemplate.find("from Reservation");
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

        if (logger.isDebugEnabled()) {
            logger.debug("reservationId set to: " + reservation.getId());
        }
        return true;
    }

    public void removeReservation(Long id) {
        hibernateTemplate.delete(getReservation(id));
    }

	public boolean isConflict(Reservation reservation) {
		//increment start time and end time to prevent overlap conflicts for 
		//reservations that begin where another ends
		Date startDateTime = new Date(reservation.getStartDateTime().getTime() + 1);
		Date endDateTime = new Date(reservation.getEndDateTime().getTime() - 1);
		
		Object [] params = new Object[5];
		params[0] = reservation.getRoom();
		params[1] = startDateTime;
		params[2] = endDateTime;
		params[3] = startDateTime;
		params[4] = endDateTime;
		//this HQL checks for three conditions:
		//1) If the start time overlaps with and existing reservation
		//2) If the end time overlaps with an existing reservation
		//3) If the entire reservation is longer than any existing reservation
		List list = hibernateTemplate.find("from Reservation r where r.room = ? and ((? between r.startDateTime and r.endDateTime) or ((? between r.startDateTime and r.endDateTime)) or (? < r.startDateTime and ? > r.endDateTime))", params);
		logger.debug("Retreiving matching reservations, count: " + list.size());
		if (list.size() > 1){
			logger.debug("###1");
			return true;
		} else if (list.size() == 1){
			logger.debug("###2");
			Reservation res = (Reservation) list.iterator().next();
			logger.debug("###3");
			//if this reservation was never saved then this is certainly a conflict 
			//or if the ID is different then it is also a conflict
			logger.debug("###4");
			if (reservation.getId() == null || (!res.getId().equals(reservation.getId()))){
				logger.debug("###5");
				return true;
			}
		}
		/*
		//in case that passed need to check if the new reservation is longer on both ends
		list = hibernateTemplate.find("from Reservation r where r.room = ? and (? < r.startDateTime and ? > r.endDateTime)", params);
		logger.debug("Retreiving matching reservations, count: " + list.size());
		System.out.println("\t2.1");
		if (list.size()> 1){
			System.out.println("\t2.2");
			return true;
		} else if (list.size() == 1){
			System.out.println("\t2.3");
			Reservation res = (Reservation) list.iterator().next();
			//if this reservation was never saved...
			//or if the ID is different then it is a conflict
			if (reservation.getId() == null || (!res.getId().equals(reservation.getId()))){
				System.out.println("\t2.5");
				return true;
			}
		}*/
		logger.debug("###6");
		return false;
	}
}