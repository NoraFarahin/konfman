package com.jmw.konfman.service;

import java.util.List;

import com.jmw.konfman.model.Reservation;

public interface ReservationManager {
    public List getReservations();
    public Reservation getReservation(String reservationId);
    public void saveReservation(Reservation reservation);
    public void removeReservation(String reservationId);
}
