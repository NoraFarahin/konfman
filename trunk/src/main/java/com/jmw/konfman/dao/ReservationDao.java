package com.jmw.konfman.dao;

import java.util.List;

import com.jmw.konfman.model.Reservation;


public interface ReservationDao extends Dao {
    public List getReservations();
    public Reservation getReservation(Long reservationId);
    public void saveReservation(Reservation reservation);
    public void removeReservation(Long reservationId);
}
