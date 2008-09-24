package com.jmw.konfman.model;

public class Hour {
	
	private String time;
	private Reservation reservation;
	
	public Hour(){ }
	
	public Hour(String time, Reservation reservation){
		this.time = time;
		this.reservation = reservation;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the reservation
	 */
	public Reservation getReservation() {
		return reservation;
	}

	/**
	 * @param reservation the reservation to set
	 */
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

}
