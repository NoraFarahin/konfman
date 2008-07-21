package com.jmw.konfman.service;

import java.util.List;

import com.jmw.konfman.model.Reservation;

public interface ReservationManager {
    /**
     * Gets all reservations
     * @return a list of reservations
     */
	public List getReservations();
    
    /**
     * Gets a specifc reservation
     * @param reservationId id number of the reservation to get
     * @return the selected reservation
     */
    public Reservation getReservation(String reservationId);
    
    /**
     * Save or update a reservation
     * @param reservation the reservation to save
     * @return true if successful with no conflicts false if there is a conflict
     */
    public boolean saveReservation(Reservation reservation);
    
    /**
     * Remove an existing reservation
     * @param reservationId
     */
    public void removeReservation(String reservationId);
    
    /**
     * Tests if this reservation conflicts with an existing one
     * @param reservation the new reservation
     * @return true if there is a conflict false if not
     */
    public boolean isConflict(Reservation reservation);
}
