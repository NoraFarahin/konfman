package com.jmw.konfman.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.jmw.konfman.dao.ReservationDao;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.service.ReservationManager;

import java.util.List;

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
}
