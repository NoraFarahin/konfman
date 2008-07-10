package com.jmw.konfman.model.wrapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jmw.konfman.dao.RoomDao;
import com.jmw.konfman.dao.UserDao;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;

/**
 * Represents a room in the Konfman application
 * @author judahw
 *
 */
public class ReservationWrapper {
	
	private Reservation reservation;
    private Long roomId;
    private Long userId;
    private String date;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    DateFormat tf = new SimpleDateFormat("h:mm aa");
	private String startTime;
	private String endTime;
	
   @Autowired
	private RoomDao roomDao;
   @Autowired
	private UserDao userDao;
	
	
	public ReservationWrapper(){
		reservation = new Reservation();
	}
	
	public ReservationWrapper(Reservation reservation){
		this.reservation = reservation;
	}
	
	public void setReservation(Reservation reservation){
		this.reservation = reservation;
	}
	
	public Reservation getReservation(){
		try {
			reservation.setStartDateTime(df.parse(date + " " + startTime));
			reservation.setEndDateTime(df.parse(date + " " + endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return reservation;
	}
	
    /**
	 * @return the id
	 */
	public Long getId() {
		return reservation.getId();
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		reservation.setId(id);
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return reservation.getComment();
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		reservation.setComment(comment);
	}

    public void setRoom(Room room){
    	reservation.setRoom(room);
    }
    
    public Room getRoom(){
    	return reservation.getRoom();
    }

    /**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		reservation.setUser(user);
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return reservation.getUser();
	}
    
    public void setDate(String date){
    	this.date = date; 
    }

    public String getDate(){
    	if (date != null){
    		return date;
    	}
    	return DateFormat.getDateInstance(DateFormat.SHORT).format(reservation.getStartDateTime());
    }
    
    public void setStartTime(String time) throws Exception{
    	this.startTime = time;
    	//startDateTime = df.parse(date + " " + time);
    }
    
    public String getStartTime(){
    	if (startTime != null){
    		return startTime;
    	} else if (reservation.getStartDateTime() != null){
    		return tf.format(reservation.getStartDateTime());
    	}
    	return "";
    }
    
    public void setEndTime(String time) throws Exception{
    	this.endTime = time;
    	//startDateTime = df.parse(date + " " + time);
    }

    public String getEndTime(){
    	if (endTime != null){
    		return endTime;
    	} else if (reservation.getEndDateTime() != null){
    		return tf.format(reservation.getEndDateTime());
    	}
    	return "";
    }

    public void setUserId(Long userId){
    	reservation.setUser(userDao.getUser(userId));
    }
    
    public void setRoomId(Long userId){
    	reservation.setRoom(roomDao.getRoom(userId));
    }

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public List getUsers(){
		return userDao.getUsers();
	}
}
