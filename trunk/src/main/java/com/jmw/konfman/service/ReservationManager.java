package com.jmw.konfman.service;

import java.util.Date;
import java.util.List;

import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;

public interface ReservationManager {
    /**
     * Gets all reservations
     * @return a list of reservations
     */
	public List getReservations();
    
    /**
     * Gets all of the user's reservations (past and future)
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
     * Gets all of the room's reservations (present and future)
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
	 * Gets the reservations that the user has for the given day
	 * @param user
	 * @param date
	 * @return a list of reservations
	 */
	public List getDailyUserReservations(User user, Date date);
	
	/**
	 * Gets the reservations that the user has for the week of the date parameter
	 * @param user
	 * @param date
	 * @return a list of reservations
	 */
	public List getWeeklyUserReservations(User user, Date date);

	/**
	 * Gets the reservations that the user has for the month of the date para
	 * @param user
	 * @param date
	 * @return a list of reservations
	 */
	public List getMonthlyUserReservations(User user, Date date);
	
	/**
	 * Gets the reservations that the room has for the given day
	 * @param room
	 * @param date
	 * @return
	 */
	public List getDailyRoomReservations(Room room, Date date);

	/**
	 * Gets the reservations that the room has for the week of the date parameter
	 * @param room
	 * @param date
	 * @return
	 */
	public List getWeeklyRoomReservations(Room room, Date date);

	/**
	 * Gets the reservations that the room has for the week of the date parameter
	 * @param room
	 * @param date
	 * @return
	 */
	public List getMonthlyRoomReservations(Room room, Date date);
	
	
	
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
