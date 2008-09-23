package com.jmw.konfman.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmw.konfman.dao.ReservationDao;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.ReservationManager;

@Service(value = "reservationManager")
public class ReservationManagerImpl implements ReservationManager {
    private final Log log = LogFactory.getLog(ReservationManagerImpl.class);
    @Autowired
    ReservationDao dao;

    public void setReservationDao(ReservationDao dao) {
        this.dao = dao;
    }

    public List getReservations() {
        return dao.getReservations();
    }

    public Reservation getReservation(String reservationId) {
        return dao.getReservation(Long.valueOf(reservationId));
    }

    public boolean saveReservation(Reservation reservation) {
        return dao.saveReservation(reservation);
    }

    public void removeReservation(String reservationId) {
        dao.removeReservation(Long.valueOf(reservationId));
    }

	public boolean isConflict(Reservation reservation) {
		return dao.isConflict(reservation);
	}

	public List getAllUserReservations(User user) {
		return dao.getAllUserReservations(user);
	}

	public List getCurrentUserReservations(User user) {
		return dao.getCurrentUserReservations(user);
	}

	public List getPastUserReservations(User user) {
		return dao.getPastUserReservations(user);
	}

	public List getAllRoomReservations(Room room) {
		return dao.getAllRoomReservations(room);
	}

	public List getCurrentRoomReservations(Room room) {
		return dao.getCurrentRoomReservations(room);
	}

	public List getPastRoomReservations(Room room) {
		return dao.getPastRoomReservations(room);
	}

	public List getDailyRoomReservations(Room room, Date date) {
		Reservation reservation = new Reservation();
		reservation.setRoom(room);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		reservation.setStartDateTime(new Date(calendar.getTimeInMillis()));

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		reservation.setEndDateTime(new Date(calendar.getTimeInMillis()));
		log.debug("Start date for daily room search: " + reservation.getStartDateTime());
		log.debug("End   date for daily room search: " + reservation.getEndDateTime());

		return dao.getIntervalReservations(reservation);
	}

	public List getDailyUserReservations(User user, Date date) {
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		reservation.setStartDateTime(new Date(calendar.getTimeInMillis()));

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		reservation.setEndDateTime(new Date(calendar.getTimeInMillis()));
		log.debug("Start date for daily search: " + reservation.getStartDateTime());
		log.debug("End   date for daily search: " + reservation.getEndDateTime());
		return dao.getIntervalReservations(reservation);
	}

	public List getMonthlyRoomReservations(Room room, Date date) {
		Reservation reservation = new Reservation();
		reservation.setRoom(room);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		reservation.setStartDateTime(new Date(calendar.getTimeInMillis()));

		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		reservation.setEndDateTime(new Date(calendar.getTimeInMillis()));
		log.debug("Start date for monthly search: " + reservation.getStartDateTime());
		log.debug("End   date for monthly search: " + reservation.getEndDateTime());
		
		return dao.getIntervalReservations(reservation);
	}

	public List getMonthlyUserReservations(User user, Date date) {
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		reservation.setStartDateTime(new Date(calendar.getTimeInMillis()));

		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		reservation.setEndDateTime(new Date(calendar.getTimeInMillis()));
		log.debug("Start date for monthly search: " + reservation.getStartDateTime());
		log.debug("End   date for monthly search: " + reservation.getEndDateTime());
		
		return dao.getIntervalReservations(reservation);
	}

	public List getWeeklyRoomReservations(Room room, Date date) {
		Reservation reservation = new Reservation();
		reservation.setRoom(room);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		reservation.setStartDateTime(new Date(calendar.getTimeInMillis()));

		calendar.set(Calendar.DAY_OF_WEEK, 7);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		reservation.setEndDateTime(new Date(calendar.getTimeInMillis()));
		log.debug("Start date for weekly room search: " + reservation.getStartDateTime());
		log.debug("End   date for weekly room search: " + reservation.getEndDateTime());

		return dao.getIntervalReservations(reservation);
	}

	public List getWeeklyUserReservations(User user, Date date) {
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		reservation.setStartDateTime(new Date(calendar.getTimeInMillis()));

		calendar.set(Calendar.DAY_OF_WEEK, 7);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		reservation.setEndDateTime(new Date(calendar.getTimeInMillis()));
		log.debug("Start date for weekly room search: " + reservation.getStartDateTime());
		log.debug("End   date for weekly room search: " + reservation.getEndDateTime());

		return dao.getIntervalReservations(reservation);
	}

}
