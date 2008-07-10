package com.jmw.konfman.dao.hibernate;

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
 * @author Matt Raible
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

    public void saveReservation(Reservation reservation) {
        hibernateTemplate.saveOrUpdate(reservation);

        if (logger.isDebugEnabled()) {
            logger.debug("reservationId set to: " + reservation.getId());
        }
    }

    public void removeReservation(Long id) {
        hibernateTemplate.delete(getReservation(id));
    }
}
