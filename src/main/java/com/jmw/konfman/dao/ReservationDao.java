package com.jmw.konfman.dao;

import java.util.Date;
import java.util.List;

import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;


public interface ReservationDao extends Dao {
    /**
     * Returns all reservations stored in the system
     * @return the reservations as a List
     */
	public List getReservations();
	
    /**
     * Gets user's current reservations (those which for today and in the future)
     * @return a list of reservations
     */
	public List getAllUserReservations(User user);

	/**
     * Gets user's current reservations (those which for today and in the future)
     * @return a list of reservations
     */
	public List getCurrentUserReservations(User user);

    /**
     * Gets user's past reservations (those which are before today)
     * @return a list of reservations
     */
	public List getPastUserReservations(User user);

	/**
     * Gets room's current reservations (those which for today and in the future)
     * @return a list of reservations
     */
	public List getAllRoomReservations(Room room);

	/**
     * Gets room's current reservations (those which for today and in the future)
     * @return a list of reservations
     */
	public List getCurrentRoomReservations(Room room);

    /**
     * Gets room's past reservations (those which are before today)
     * @return a list of reservations
     */
	public List getPastRoomReservations(Room room);

	/**
     * Gets room's reservations for a specific interval
	 * 
	 * @param reservation the template reservation with the properties to be searched for
	 * @return a list of reservations
	 */
	public List getIntervalReservations(Reservation reservation);

	/**
	 * Gets a specific reservation specified by reservationId
	 * @param reservationId the unique ID of the reservation sought
	 * @return the reservation
	 */
    public Reservation getReservation(Long reservationId);
    
    /**
     * Save changes to a reservation
     * @param reservation the reservation to be changed
     * @return false if the reservation was not saved due to a conflict 
     */
    public boolean saveReservation(Reservation reservation);
    
    /**
     * Removes a reservation from the database
     * @param reservationId the ID of the reservation to remove
     */
    public void removeReservation(Long reservationId);
    
    /**
     * Determines if there is a conflict between this reservation and an existing one
     * @param reservation the new reservation
     * @return true if there is a conflict, false if there is no conflict
     */
    public boolean isConflict(Reservation reservation);
}
