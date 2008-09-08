package com.jmw.konfman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmw.konfman.dao.ReservationDao;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.ReservationManager;

@Service(value = "reservationManager")
public class ReservationManagerImpl implements ReservationManager {
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

}
